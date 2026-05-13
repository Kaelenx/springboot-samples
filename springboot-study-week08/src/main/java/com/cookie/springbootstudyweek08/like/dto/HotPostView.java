package com.cookie.springbootstudyweek08.like.dto;

public record HotPostView(
        int rank,
        Long postId,
        long likeCount
) {
}
