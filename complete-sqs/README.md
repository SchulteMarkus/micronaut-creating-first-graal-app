# Creating your first Micronaut Graal function consuming a SQS event #

**Setup**

In AWS, create a SQS whichs triggers an AWS Lambda as described on
https://docs.aws.amazon.com/lambda/latest/dg/with-sqs.html

## Plain old jar / AWS Lambda Java8 runtime

    $ ./gradlew clean assemble

The build result _complete-sqs-0.1-all.jar_ can be used in 
https://docs.aws.amazon.com/lambda/latest/dg/java-programming-model.html

```bash
$ java -jar build/libs/complete-sqs-0.1-all.jar -d "{\"Records\": [{\"search\":\"Greach\"}]}"
19:10:12.825 [main] INFO  i.m.context.env.DefaultEnvironment - Established active environments: [function]
19:10:13.644 [main] INFO  example.micronaut.ConferenceService - 1 records from SQS event given
19:10:13.645 [main] INFO  example.micronaut.Function - found 1 conferences
[{"name":"Greach"}]
```

### Deploy to AWS

```bash
$ aws lambda update-function-configuration --function-name <function-name> --runtime java8 \
    --handler example.micronaut.Function::apply
$ aws lambda update-function-code --function-name <function-name> \
    --zip-file fileb://build/libs/complete-sqs-0.1-all.jar ```
```

## Binary / AWS Lambda custom runtime

    $ ./docker-build.sh

The build result _build/awsfunction.zip_ can be used in 
https://docs.aws.amazon.com/lambda/latest/dg/runtimes-custom.html

For local testing, you can use build/micronaut-graal-sqs-function

```bash
$ build/micronaut-graal-sqs-function -x -d "{\"Records\": [{\"search\":\"Greach\"}]}"
17:57:53.422 [main] INFO  i.m.context.env.DefaultEnvironment - Established active environments: [function]
17:57:53.431 [main] INFO  example.micronaut.ConferenceService - 1 records from SQS event given
17:57:53.431 [main] INFO  example.micronaut.Function - found 1 conferences
[{"name":"Greach"}]
```    

### Deploy to AWS

```bash
$ aws lambda update-function-configuration --function-name <function-name> --runtime provided \
    --handler micronaut-graal-sqs-function
$ aws lambda update-function-code --function-name <function-name> \
    --zip-file fileb://build/awsfunction.zip
```