package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PhotoDataFormatter implements Function<PhotoData, String> {
    @Override
    public String apply(PhotoData photoData) {
        return String.format("[%d] %s", photoData.getId(), photoData.getTitle());
    }
}
