package com.example.demo;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@AllArgsConstructor
@Profile("!test")
public class DemoApplication implements ApplicationRunner {
    private static Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    private final PhotoService photoService;
    private final PhotoDataFormatter photoDataFormatter;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args).close();
    }

    @Bean
    private static WebClient webClient(@Value("${server.uri}") String serverUri) {
        return WebClient.builder()
                .baseUrl(serverUri)
                .defaultHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }

    @Override
    public void run(ApplicationArguments args) {
        String[] sourceArgs = args.getSourceArgs();

        if (sourceArgs.length == 0) {
            photoService.getAllPhotos()
                    .stream()
                    .map(photoDataFormatter)
                    .forEach(System.out::println);
        } else {
            for (String album : sourceArgs) {
                try {
                    int albumId = Integer.parseInt(album);
                    photoService.getPhotosInAlbum(albumId)
                            .stream()
                            .map(photoDataFormatter)
                            .forEach(System.out::println);
                } catch (NumberFormatException e) {
                    LOG.warn("Cannot fetch photos for album: {}", album);
                }
            }
        }
    }
}
