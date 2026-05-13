package com.cookie.springbootstudyweek08.stats;

public final class PostViewStatsRedisKey {

    public static final String POST_VIEW_COUNT_PREFIX = "week08:post:view:count:";

    private PostViewStatsRedisKey() {
    }

    public static String countKey(Long postId) {
        return POST_VIEW_COUNT_PREFIX + postId;
    }
}
