package contextos;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TestFront {

       @Test
       @Category(FrontTest.class)
       public void testFrontFirst() {
          System.out.println("TEST FRONT 1");
       }

        @Test
        @Category(FrontTest.class)
        public void testFrontSecond(){
           System.out.println("TEST FRONT 2");
        }
}
