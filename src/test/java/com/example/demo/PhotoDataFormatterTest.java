package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PhotoDataFormatterTest {
    private final PhotoDataFormatter photoDataFormatter = new PhotoDataFormatter();

    @Test
    void givenValidPhotoData_FormatsPhotoData() {
        int id = 2;
        String title = "title";
        PhotoData photoData = new PhotoData(4, id, title, "url", "thumbnailUrl");

        String actual = photoDataFormatter.apply(photoData);

        assertThat(actual).contains(title);
        assertThat(actual).contains("[" + id + "]");
        assertThat(actual).isEqualTo("[" + id + "] " + title);
    }
}