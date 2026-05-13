package com.cookie.springbootstudyweek08.like;

public final class PostLikeRedisKey {

    public static final String LIKE_USERS_PREFIX = "week08:post:like:users:";
    public static final String RANKING_KEY = "week08:post:like:ranking";

    private PostLikeRedisKey() {
    }

    public static String usersKey(Long postId) {
        return LIKE_USERS_PREFIX + postId;
    }
}
