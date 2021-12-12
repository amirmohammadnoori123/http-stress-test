package http.stress.mci.test;


import http.stress.mci.test.util.CachedGetRequest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class StressManager {

    private final AtomicLong aLong = new AtomicLong();

    private final ExecutorService requestExecutor = Executors.newFixedThreadPool(1);


    public StressManager() {
        doStress();
    }

    private void doStress() {
        for (int i = 0; i < 1; i++) {
            requestExecutor.execute(() -> {
                final HttpClient s = HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .followRedirects(HttpClient.Redirect.ALWAYS)
                        .connectTimeout(Duration.ofSeconds(1))
                        .proxy(ProxySelector.of(new InetSocketAddress("167.71.244.209", 8086)))
                        .build();
                while (true){
                    try {
                        try {
                            Thread.sleep(25);
                            s.send(CachedGetRequest.getRandomRequest(), HttpResponse.BodyHandlers.ofString());
                            System.out.println(aLong.incrementAndGet());
                            System.out.println(Thread.currentThread().getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    catch (InterruptedException e) {
                        throw new IllegalStateException("task interrupted", e);
                    }
                }
            });
        }
    }

}