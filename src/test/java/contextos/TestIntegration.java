package contextos;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TestIntegration {

       @Test
       @Category(IntegrationTest.class)
       public void testIntegrationFirst() {
          System.out.println("TEST INTEGRATION 1");
       }

        @Test
        @Category(IntegrationTest.class)
        public void testIntegrationSecond(){
           System.out.println("TEST INTEGRATION 2");
        }
}
