# Creating your first Micronaut Graal function consuming a SQS event #

**Setup**

In AWS, create a SQS whichs triggers an AWS Lambda as described on
https://docs.aws.amazon.com/lambda/latest/dg/with-sqs.html

## Usage

See [Makefile](Makefile).

### Plain old jar / AWS Lambda Java8 runtime

    $ make assemble


The build result _complete-sqs-0.1-all.jar_ can be used in 
https://docs.aws.amazon.com/lambda/latest/dg/java-programming-model.html

    $ make invoke-lambda-as-jar-locally
    $ # or
    $ make lambda-as-jar-deploy-to-aws 
    $ make invoke-lambda-on-aws       

## Binary / AWS Lambda custom runtime

    $ make assemble-graal

The build result _build/awsfunction.zip_ can be used in 
https://docs.aws.amazon.com/lambda/latest/dg/runtimes-custom.html

For local testing, you can use _build/micronaut-graal-sqs-function_

    $ make invoke-lambda-as-graal-locally
    $ # or
    $ make lambda-as-graal-deploy-to-aws 
    $ make invoke-lambda-on-aws       