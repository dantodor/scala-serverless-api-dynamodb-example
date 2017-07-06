package api_example.util

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2._

object AWSDynamoDB extends {
  def client(): AmazonDynamoDBAsync = {
    val client = AmazonDynamoDBAsyncClient.asyncBuilder().withEndpointConfiguration(
      new AwsClientBuilder.EndpointConfiguration("https://dynamodb.us-east-1.amazonaws.com", "us-east-1")
    ).build()
    client
  }
}
