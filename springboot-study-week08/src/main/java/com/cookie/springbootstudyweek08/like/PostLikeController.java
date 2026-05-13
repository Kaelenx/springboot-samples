package com.cookie.springbootstudyweek08.like;

import com.cookie.springbootstudyweek08.like.dto.HotPostView;
import com.cookie.springbootstudyweek08.like.dto.LikeActionRequest;
import com.cookie.springbootstudyweek08.like.dto.PostLikeStatsView;
import com.cookie.springbootstudyweek08.like.dto.PostLikeView;
import com.cookie.springbootstudyweek08.sms.dto.ApiResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Validated
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/likes")
    public ApiResult<PostLikeView> like(
            @PathVariable @Positive(message = "文章 ID 必须大于 0") Long postId,
            @RequestBody @Valid LikeActionRequest request) {
        return ApiResult.success(postLikeService.like(postId, request.userId()));
    }

    @DeleteMapping("/{postId}/likes/{userId}")
    public ApiResult<PostLikeView> unlike(
            @PathVariable @Positive(message = "文章 ID 必须大于 0") Long postId,
            @PathVariable @Positive(message = "用户 ID 必须大于 0") Long userId) {
        return ApiResult.success(postLikeService.unlike(postId, userId));
    }

    @GetMapping("/{postId}/likes/stats")
    public ApiResult<PostLikeStatsView> stats(
            @PathVariable @Positive(message = "文章 ID 必须大于 0") Long postId,
            @RequestParam(required = false) @Positive(message = "用户 ID 必须大于 0") Long userId) {
        return ApiResult.success(postLikeService.stats(postId, userId));
    }

    @GetMapping("/likes/ranking")
    public ApiResult<List<HotPostView>> ranking(
            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "limit 不能小于 1")
            @Max(value = 20, message = "limit 不能大于 20") Integer limit) {
        return ApiResult.success(postLikeService.ranking(limit));
    }
}
