package com.cookie.springbootstudyweek08.stats.dto;

public record PostViewStatsView(
        Long postId,
        long viewCount
) {
}
