package org.bootstrap.post.utils;

import org.bootstrap.post.entity.Post;

public class FrontUrlGenerator {
    private final static String FRONTEND_BASE_URL = "http://localhost:5173/";

    public static String createFrontUrl(Post post, String moldevId) {
        return FRONTEND_BASE_URL + moldevId + "/" + post.getCategory() + "/" + post.getId();
    }
}
