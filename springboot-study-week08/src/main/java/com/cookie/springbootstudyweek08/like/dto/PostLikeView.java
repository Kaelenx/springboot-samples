package com.cookie.springbootstudyweek08.like.dto;

public record PostLikeView(
        Long postId,
        Long userId,
        boolean liked,
        long likeCount
) {
}
