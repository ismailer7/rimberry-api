package io.idev.storeapi;

import java.time.format.DateTimeFormatter;

public class StoreApiConstants {
    private StoreApiConstants() {
        throw new IllegalStateException("Constants cannot be instantiated");
    }

    public static final String API_BASE_URI = "/api/v1/";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
}
