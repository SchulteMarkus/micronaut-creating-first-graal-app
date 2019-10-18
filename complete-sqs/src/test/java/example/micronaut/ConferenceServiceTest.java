package example.micronaut;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.annotation.MicronautTest;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class ConferenceServiceTest {

  private static final TypeReference<HashMap<String, Object>> MAP_TYPE_REFERENCE =
      new TypeReference<>() {};

  @Inject private ObjectMapper objectMapper;

  @Inject private ConferenceService conferenceService;

  @Test
  void getConference() throws IOException {
    final String sqsEventJson = "{\"Records\": [{\"name\":\"Greach\"}]}";
    final Map<String, Object> sqsEvent = objectMapper.readValue(sqsEventJson, MAP_TYPE_REFERENCE);

    final Collection<Conference> conferences = conferenceService.getConferences(sqsEvent);

    Assertions.assertEquals(1, conferences.size());
  }
}
