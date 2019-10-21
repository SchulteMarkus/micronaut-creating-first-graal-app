package example.micronaut;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.core.util.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton // <1>
public class ConferenceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceService.class);

  private static final List<Conference> CONFERENCES =
      Arrays.asList(
          new Conference("Greach"),
          new Conference("GR8Conf EU"),
          new Conference("Micronaut Summit"),
          new Conference("Devoxx Belgium"),
          new Conference("Oracle Code One"),
          new Conference("CommitConf"),
          new Conference("Codemotion Madrid"));

  private final ObjectMapper objectMapper;

  @Inject
  public ConferenceService(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public Collection<Conference> getConferences(final SQSEvent sqsEvent) { // <2>
    final List<SQSMessage> sqsMessages = sqsEvent.getRecords();
    LOGGER.info("{} records from SQS event given", sqsMessages.size());

    if (sqsMessages.isEmpty()) {
      return Collections.emptyList();
    }

    final List<Conference> allMatchingConferences = new ArrayList<>();
    for (final SQSMessage sqsMessage : sqsMessages) {
      final String messageBody = sqsMessage.getBody();
      final JsonNode nameContainer;
      try {
        LOGGER.debug("Reading {}", messageBody);
        nameContainer = objectMapper.readTree(messageBody);
      } catch (final IOException e) {
        LOGGER.warn("Cannot read {}", messageBody);
        continue;
      }

      final String conferenceName = nameContainer.get("conferenceName").asText();
      if (StringUtils.isNotEmpty(conferenceName)) {
        final List<Conference> matchingConferences =
            CONFERENCES.stream()
                .filter(conference -> conferenceName.equalsIgnoreCase(conference.getName()))
                .collect(Collectors.toList());
        allMatchingConferences.addAll(matchingConferences);
      }
    }

    return allMatchingConferences;
  }
}
