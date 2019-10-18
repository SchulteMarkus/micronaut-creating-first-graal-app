package example.micronaut;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class FunctionTest {

  @Test
  void main() throws IOException {
    Function.main("-d", "{\"Records\": []}");
  }
}
