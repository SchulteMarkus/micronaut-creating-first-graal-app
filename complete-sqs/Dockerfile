FROM oracle/graalvm-ce as graalvm
COPY . /home/app/micronaut-graal-function
WORKDIR /home/app/micronaut-graal-function
RUN gu install native-image
RUN native-image --no-server -cp build/libs/complete-sqs*-all.jar