package example.micronaut;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.function.FunctionBean;
import io.micronaut.function.executor.FunctionInitializer;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FunctionBean
@TypeHint(SQSEvent.class)
public class Function extends FunctionInitializer
    implements java.util.function.Function<Map<String, Object>, Collection<Conference>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(Function.class);

  @Inject ConferenceService conferenceService;

  public static void main(final String... args) throws IOException {
    final Function function = new Function();
    function.run(args, (context) -> function.apply((Map<String, Object>) context.get(Map.class)));
  }

  @Override
  public Collection<Conference> apply(final Map<String, Object> sqsEvent) {
    final Collection<Conference> conferences = conferenceService.getConferences(sqsEvent);
    LOGGER.info("found {} conferences", conferences.size());

    return conferences;
  }
}
