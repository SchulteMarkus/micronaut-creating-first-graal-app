package example.micronaut;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.function.FunctionBean;
import io.micronaut.function.executor.FunctionInitializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FunctionBean(ConsumeSqsFunction.FUNCTION_BEAN_NAME)
@TypeHint({SQSEvent.class, SQSEvent.SQSMessage.class, SQSEvent.MessageAttribute.class})
public class ConsumeSqsFunction extends FunctionInitializer
    implements Function<SQSEvent, Collection<Conference>> {

  public static final String FUNCTION_BEAN_NAME = "consume-sqs-lambda";

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeSqsFunction.class);

  @Inject ConferenceService conferenceService;

  public static void main(final String... args) throws IOException {
    LOGGER.info("Input: " + Arrays.toString(args));

    final ConsumeSqsFunction consumeSqsFunction = new ConsumeSqsFunction();
    consumeSqsFunction.run(
        args, (context) -> consumeSqsFunction.apply(context.get(SQSEvent.class)));
  }

  @Override
  public Collection<Conference> apply(final SQSEvent sqsEvent) {
    LOGGER.info(sqsEvent.toString());
    final Collection<Conference> conferences = conferenceService.getConferences(sqsEvent);
    LOGGER.info("found {} conferences", conferences.size());

    return conferences;
  }
}
