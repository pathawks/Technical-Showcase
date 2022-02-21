package com.example.demo;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class PhotoData {
    int albumId;
    int id;
    String title;
    String url;
    String thumbnailUrl;
}
