package http.stress.mci.test.util;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CachedGetRequest {

    private static final FileHelper fileHelper = new FileHelper();

    private static final List<HttpRequest> requests = new ArrayList<>();
    private static final Random random = new Random();

    static {
        fillRequest();
    }

    private static void fillRequest() {
        try {
            fileHelper.readFileByLines().forEach(domains -> {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://"+domains))
                        .timeout(Duration.ofMinutes(1))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();

                requests.add(request);
            });
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static HttpRequest getRandomRequest() {
        return requests.get(random.nextInt(requests.size()));
    }
}
