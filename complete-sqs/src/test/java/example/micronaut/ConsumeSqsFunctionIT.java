package example.micronaut;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import io.micronaut.function.client.FunctionClient;
import io.micronaut.http.annotation.Body;
import io.micronaut.test.annotation.MicronautTest;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;
import javax.inject.Named;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class ConsumeSqsFunctionIT {

  @Inject private LambdaClient client;

  @Test
  void apply() {
    final SQSMessage sqsMessage = new SQSMessage();
    sqsMessage.setBody("{\"conferenceName\":\"CommitConf\"}");
    final SQSEvent sqsEvent = new SQSEvent();
    sqsEvent.setRecords(Collections.singletonList(sqsMessage));

    final Collection<Conference> conferences = client.apply(sqsEvent);

    Assertions.assertFalse(conferences.isEmpty());
  }

  /* void fastPath() {
    ConsumeSqsFunction.main("-d", "{\"Records\": [{\"body\": \"{\\\"conferenceName\\\":\\\"Greach\\\"}\"}]}");
  } */

  @FunctionClient
  interface LambdaClient {
    @Named(ConsumeSqsFunction.FUNCTION_BEAN_NAME)
    Collection<Conference> apply(@Body SQSEvent event);
  }
}
