package api_example.handler

import java.util
import java.util.Date

import api_example.model.{ApiGatewayResponse, Transaction}
import api_example.util.AWSDynamoDB
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.gu.scanamo.syntax._
import com.gu.scanamo.{Scanamo, Table}
import org.json4s._
import org.json4s.jackson.JsonMethods._

class PostTransactionHandler extends RequestHandler[util.Map[String, Any], ApiGatewayResponse] {

  implicit val formats = DefaultFormats

  def handleRequest(input: util.Map[String, Any], context: Context): ApiGatewayResponse = {

    try {
      val pathParameters = input.get("pathParameters").asInstanceOf[util.Map[String, String]]
      val body = parse(input.get("body").asInstanceOf[String])

      val client = AWSDynamoDB.client()

      val table = Table[Transaction]("transactions_table")

      val tx =
        Transaction(
          pathParameters.get("account_id"),
          new Date(System.currentTimeMillis()),
          (body \ "transaction_id").extract[String],
          (body \ "amount").extract[String].toFloat)

      val ops = for {
        _ <- table.put(tx)
        res <- table.get('account_id -> tx.account_id and 'transaction_date -> tx.transaction_date.getTime.toString)
      } yield res

      Scanamo.exec(client)(ops) match {
        case Some(Right(_)) =>
          ApiGatewayResponse(200, "Success!", ApiGatewayResponse.defaultHeaders, isBase64Encoded = false)
        case Some(Left(err)) =>
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
