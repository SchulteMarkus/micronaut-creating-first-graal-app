assemble:
	./gradlew clean assemble

assemble-graal:
	./docker-build.sh

lambda-as-jar-deploy-to-aws:
  aws lambda update-function-configuration --function-name complete-sqs --runtime java8 --handler example.micronaut.ConsumeSqsFunction::apply
	aws lambda update-function-code --function-name complete-sqs --zip-file fileb://build/libs/complete-sqs-0.1-all.jar

lambda-as-graal-deploy-to-aws:
	aws lambda update-function-configuration --function-name complete-sqs --runtime provided --handler micronaut-graal-sqs-function
	aws lambda update-function-code --function-name complete-sqs --zip-file fileb://build/awsfunction.zip

invoke-lambda-as-jar-locally:
	java -XX:TieredStopAtLevel=1 -noverify -jar build/libs/complete-sqs-0.1-all.jar -x -d "{\"Records\": [{\"body\": \"{\\\"conferenceName\\\":\\\"Greach\\\"}\"}]}"

invoke-lambda-as-graal-locally:
	build/micronaut-graal-sqs-function -x -d "{\"Records\": [{\"body\": \"{\\\"conferenceName\\\":\\\"Greach\\\"}\"}]}"

invoke-lambda-on-aws:
	aws lambda invoke --function-name complete-sqs --payload '{\"Records\": [{\"body\": \"{\\\"conferenceName\\\":\\\"Greach\\\"}\"}]}' build/response.txt
	cat build/response.txt