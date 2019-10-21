package example.micronaut;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import io.micronaut.test.annotation.MicronautTest;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class ConferenceServiceTest {

  @Inject private ConferenceService conferenceService;

  @Test
  void getConference() {
    final SQSMessage sqsMessage = new SQSMessage();
    sqsMessage.setBody("{\"conferenceName\":\"Greach\"}");
    final SQSEvent sqsEvent = new SQSEvent();
    sqsEvent.setRecords(Collections.singletonList(sqsMessage));

    final Collection<Conference> conferences = conferenceService.getConferences(sqsEvent);

    Assertions.assertEquals(1, conferences.size());
  }
}
