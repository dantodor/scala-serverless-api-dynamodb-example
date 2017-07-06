## scala-serverless-api-dynamodb-example

This is the AWS Lambda + API Gateway + DynamoDB  project with 2 functions, built and deployed using [Serverless](https://serverless.com) framework.

This is based on this [Java tutorial](https://lobster1234.github.io/categories/serverless/), which has been modified to use Scala with the [scanamo ORM](https://guardian.github.io/scanamo/) and [JSON4s](https://github.com/json4s/json4s)

## Usage

1. Set up Serverless Framework and your AWS account as detailed [here](https://serverless.com/framework/docs/providers/aws/guide/quick-start/).

2. Deploy the Lambda

```
bash-3.2$ git clone https://github.com/gvonness/scala-serverless-api-dynamodb-example.git

bash-3.2$ cd scala-serverless-api-dynamodb-example

bash-3.2$ sbt assemble

bash-3.2$ serverless deploy

```

3. Hit the HTTP endpoints

4. Destroy the infrastructure

```
bash-3.2$ serverless remove
```
