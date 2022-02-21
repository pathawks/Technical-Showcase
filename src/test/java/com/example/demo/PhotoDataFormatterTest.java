package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PhotoDataFormatterTest {
    PhotoDataFormatter photoDataFormatter = new PhotoDataFormatter();

    @Test
    void givenValidPhotoData_FormatsPhotoData() {
        int expectedId = 2;
        String expectedTitle = "title";
        PhotoData photoData = new PhotoData();
        photoData.setId(expectedId);
        photoData.setTitle(expectedTitle);

        String actual = photoDataFormatter.apply(photoData);

        assertThat(actual).contains(expectedTitle);
        assertThat(actual).contains("[" + expectedId + "]");
        assertThat(actual).isEqualTo("[" + expectedId + "] " + expectedTitle);
    }
}