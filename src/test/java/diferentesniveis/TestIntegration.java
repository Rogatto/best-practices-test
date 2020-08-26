package diferentesniveis;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestIntegration {

       @Test
       @Category(IntegrationTest.class)
       public void testIntegrationFirst() throws IOException {
          System.out.println("TEST INTEGRATION 1");

           OkHttpClient client = new OkHttpClient().newBuilder()
                   .build();
           Request request = new Request.Builder()
                   .url("https://sensedia.com/en/")
                   .method("GET", null)
                   .build();
           Response response = client.newCall(request).execute();

           assertEquals(response.code(), 200);
       }

        @Test
        @Category(IntegrationTest.class)
        public void testIntegrationSecond() throws IOException {
           System.out.println("TEST INTEGRATION 2");
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://www.youtube.com/")
                    .method("GET", null)
                    .build();

            Response response = client.newCall(request).execute();
            assertEquals(response.code(), 200);
        }
}
