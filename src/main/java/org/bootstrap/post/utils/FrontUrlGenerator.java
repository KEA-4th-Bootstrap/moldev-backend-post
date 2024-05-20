package org.bootstrap.post.utils;

import org.bootstrap.post.entity.Post;

public class FrontUrlGenerator {
    private final static String FRONTEND_BASE_URL = "http://localhost:3000/";

    public static String createFrontUrl(Post post) {
        return FRONTEND_BASE_URL + post.getMoldevId() + "/" + post.getId();
    }
}
