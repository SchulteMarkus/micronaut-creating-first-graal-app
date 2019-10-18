./gradlew assemble
native-image --no-server -cp build/libs/complete-sqs*-all.jar
