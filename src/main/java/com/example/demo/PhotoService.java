package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@AllArgsConstructor
public class PhotoService {
    private final WebClient webClient;

    public List<PhotoData> getAllPhotos() {
        return getPhotosFromUri("/photos");
    }

    public List<PhotoData> getPhotosInAlbum(int albumId) {
        return getPhotosFromUri("/photos?albumId=" + albumId);
    }

    private List<PhotoData> getPhotosFromUri(String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(PhotoData.class)
                .collectList()
                .block();
    }
}
