package example.micronaut;

import io.micronaut.core.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  public Collection<Conference> getConferences(final Map<String, Object> sqsEvent) { // <2>
    final List<HashMap<String, String>> records =
        (ArrayList<HashMap<String, String>>) sqsEvent.get("Records");
    LOGGER.info("{} records from SQS event given", records.size());

    if (records.size() == 0) {
      return Collections.emptyList();
    }

    final List<Conference> allMatchingConferences = new ArrayList<>();
    for (final HashMap<String, String> record : records) {
      final String conferenceName = record.get("search");
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
