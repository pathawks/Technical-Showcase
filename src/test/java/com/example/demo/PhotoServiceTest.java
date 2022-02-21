package com.example.demo;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PhotoServiceTest {
    public static MockWebServer mockBackEnd;
    private PhotoService underTest;

    @BeforeEach
    void initialize() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        underTest = new PhotoService(WebClient.create(baseUrl));


        mockBackEnd.enqueue(new MockResponse().setBody("[\n" +
                "  {\n" +
                "    \"albumId\": 2,\n" +
                "    \"id\": 55,\n" +
                "    \"title\": \"voluptatem consequatur totam qui aut " +
                "iure est vel\",\n" +
                "    \"url\": \"https://via.placeholder.com/600/5e04a4\"," +
                "\n" +
                "    \"thumbnailUrl\": \"https://via.placeholder" +
                ".com/150/5e04a4\"\n" +
                "  }\n" +
                "]").addHeader("Content-Type", "application/json"));
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    public void should_call_api_with_no_album() throws InterruptedException {
        underTest.getAllPhotos();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/photos");
        assertThat(recordedRequest.getRequestUrl().query()).isNull();
    }

    @Test
    public void should_call_api_with_album() throws InterruptedException {
        int expectedAlbumId = 1;

        underTest.getPhotosInAlbum(expectedAlbumId);

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo(
                "/photos?albumId=" + expectedAlbumId);
    }

    @Test
    public void should_return_collection_of_photo_datas() {
        PhotoData expectedPhotoData = new PhotoData(2, 55,
                "voluptatem consequatur totam qui aut iure est vel",
                "https://via.placeholder.com/600/5e04a4",
                "https://via.placeholder.com/150/5e04a4"
        );

        List<PhotoData> photos = underTest.getAllPhotos();

        assertThat(photos).isNotEmpty();
        assertThat(photos).containsExactly(expectedPhotoData);
    }

    @Test
    public void should_return_collection_of_photo_datas_in_album() {
        int albumId = 2;
        PhotoData expectedPhotoData = new PhotoData(albumId, 55,
                "voluptatem consequatur totam qui aut iure est vel",
                "https://via.placeholder.com/600/5e04a4",
                "https://via.placeholder.com/150/5e04a4"
        );

        List<PhotoData> photos = underTest.getPhotosInAlbum(albumId);

        assertThat(photos).isNotEmpty();
        assertThat(photos).containsExactly(expectedPhotoData);
    }
}