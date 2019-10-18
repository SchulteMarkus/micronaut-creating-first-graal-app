mkdir -p build
rm -f build/micronaut-graal-sqs-function
rm -f build/awsfunction.zip

./gradlew clean assemble
docker build . -t micronaut-graal-sqs-function

docker create --name copyit micronaut-graal-sqs-function
docker cp copyit:/home/app/micronaut-graal-function/micronaut-graal-sqs-function ./
docker rm -f copyit

zip build/awsfunction.zip bootstrap micronaut-graal-sqs-function
mv micronaut-graal-sqs-function build/

echo "build/awsfunction.zip can be uploaded to AWS Lambda (custom runtime)"
