package api_example.model

import java.util.Date

import com.gu.scanamo.DynamoFormat

case class Transaction(account_id: String, transaction_date: Date, transaction_id: String, amount: Float)

object Transaction {

  case class SimpleTransaction(account_id: String, transaction_date: String, transaction_id: String, amount: String)

  def transToSimple(input: Transaction) =
    SimpleTransaction(input.account_id, input.transaction_date.getTime.toString, input.transaction_id, input.amount.toString)

  def simpleToTrans(input: SimpleTransaction) =
    Transaction(input.account_id, new Date(input.transaction_date.toLong), input.transaction_id, input.amount.toFloat)

  implicit val transactionConvert: AnyRef with DynamoFormat[Transaction] = DynamoFormat.iso(simpleToTrans)(transToSimple)
}


