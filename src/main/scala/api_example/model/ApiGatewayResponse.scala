package api_example.model

import java.util

import scala.beans.{BeanProperty, BooleanBeanProperty}
import scala.collection.JavaConverters

case class ApiGatewayResponse(@BeanProperty statusCode: Int,
                              @BeanProperty body: String,
                              @BeanProperty headers: util.Map[String, String],
                              @BooleanBeanProperty isBase64Encoded: Boolean)

object ApiGatewayResponse {

  val defaultHeaders: util.Map[String, String] = JavaConverters.mapAsJavaMap[String, String](Map("X-Powered-By" -> "AWS Lambda & Serverless"))

  def failResponse(body: String): ApiGatewayResponse = {
    ApiGatewayResponse(500, body, defaultHeaders, isBase64Encoded = false)
  }

}

