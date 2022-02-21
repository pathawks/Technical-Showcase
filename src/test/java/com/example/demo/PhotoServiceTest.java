package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PhotoServiceTest {
    MockWebServer mockBackEnd;
    PhotoService underTest;

    PhotoData samplePhotoData = PhotoData.builder()
            .id(2)
            .albumId(1)
            .title("title")
            .thumbnailUrl("thumbnailUrl")
            .url("url")
            .build();

    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

    @BeforeEach
    void initialize() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        underTest = new PhotoService(WebClient.create(baseUrl));

        String json =
                objectMapper.writeValueAsString(singletonList(samplePhotoData));
        mockBackEnd.enqueue(new MockResponse().setBody(json)
                .addHeader("Content-Type", "application/json"));
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    public void getAllPhotos_makesRequest() throws InterruptedException {
        underTest.getAllPhotos();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/photos");
    }

    @Test
    public void getPhotosInAlbum_makesRequest() throws InterruptedException {
        int expectedAlbumId = 1;

        underTest.getPhotosInAlbum(expectedAlbumId);

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo(
                "/photos?albumId=" + expectedAlbumId);
    }

    @Test
    public void getAllPhotos_returnsCollection() {
        List<PhotoData> photos = underTest.getAllPhotos();

        assertThat(photos).isNotEmpty();
        assertThat(photos).containsExactly(samplePhotoData);
    }

    @Test
    public void getPhotosInAlbum_returnsCollection() {
        int albumId = 2;

        List<PhotoData> photos = underTest.getPhotosInAlbum(albumId);

        assertThat(photos).isNotEmpty();
        assertThat(photos).containsExactly(samplePhotoData);
    }
}