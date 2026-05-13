package com.cookie.springbootstudyweek08.stats;

import com.cookie.springbootstudyweek08.sms.dto.ApiResult;
import com.cookie.springbootstudyweek08.stats.dto.PostViewIncrementRequest;
import com.cookie.springbootstudyweek08.stats.dto.PostViewStatsView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping("/api/post-view-stats")
@RequiredArgsConstructor
public class PostViewStatsController {

    private final PostViewStatsService postViewStatsService;

    @PostMapping("/{postId}/increment")
    public ApiResult<PostViewStatsView> increment(
            @PathVariable @Positive(message = "文章 ID 必须大于 0") Long postId,
            @RequestBody @Valid PostViewIncrementRequest request) {
        return ApiResult.success(postViewStatsService.increment(postId, request.delta()));
    }

    @GetMapping("/{postId}")
    public ApiResult<PostViewStatsView> stats(
            @PathVariable @Positive(message = "文章 ID 必须大于 0") Long postId) {
        return ApiResult.success(postViewStatsService.stats(postId));
    }
}
