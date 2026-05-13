package com.cookie.springbootstudyweek08.like.dto;

public record PostLikeStatsView(
        Long postId,
        long likeCount,
        boolean likedByCurrentUser
) {
}
