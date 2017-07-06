package api_example.handler

import java.util

import api_example.model.{ApiGatewayResponse, Transaction}
import api_example.util.AWSDynamoDB
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.gu.scanamo._
import com.gu.scanamo.syntax._
import org.json4s._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization._

class GetTransactionsHandler extends RequestHandler[util.Map[String, Any], ApiGatewayResponse] {

  def handleRequest(input: util.Map[String, Any], context: Context): ApiGatewayResponse = {

    implicit val formats = Serialization.formats(NoTypeHints)

    try {
      val client = AWSDynamoDB.client()

      val table = Table[Transaction]("transactions_table")

      val ops = for {
        res <- table.query('account_id -> input.get("pathParameters").asInstanceOf[util.Map[String, String]].get("account_id"))
      } yield res

      val execRes = Scanamo.exec(client)(ops)

      execRes collectFirst {
        case Left(f) => f
      } toLeft {
        execRes collect {
          case Right(r) => r
        }
      } match {
        case Right(r) =>
          ApiGatewayResponse(200, write(r), ApiGatewayResponse.defaultHeaders, isBase64Encoded = false)
        case Left(err) =>
          ApiGatewayResponse.failResponse(err.toString)
        case _ =>
          ApiGatewayResponse.failResponse("None returned")
      }
    } catch {
      case e: Exception =>
        ApiGatewayResponse.failResponse(e.toString)
    }
  }
}
