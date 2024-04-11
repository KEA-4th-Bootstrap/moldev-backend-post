package org.bootstrap.post.utils;

import org.bootstrap.post.entity.CategoryType;

public class FrontUrlGenerator {
    private final static String FRONTEND_BASE_URL = "http://localhost:5173/";
    public static String createFrontUrl (Long postId, String moldevId, CategoryType categoryType) {
        return FRONTEND_BASE_URL + moldevId + "/" + categoryType + "/" + postId;
    }
}
