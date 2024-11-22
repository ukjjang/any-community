package com.anycommunity.mvcapi.post

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import com.anycommunity.definition.post.PostSearchSortType
import com.anycommunity.mvcapi.global.MvcApiController
import com.anycommunity.mvcapi.global.security.AuthRole
import com.anycommunity.mvcapi.global.security.AuthUser
import com.anycommunity.mvcapi.post.request.PostCreateRequest
import com.anycommunity.mvcapi.post.request.PostUpdateRequest
import com.anycommunity.mvcapi.post.response.PostDetailResponse
import com.anycommunity.mvcapi.post.response.PostResponse
import com.anycommunity.mvcapi.post.response.PostSearchResponse
import com.anycommunity.usecase.post.command.PostCommandBus
import com.anycommunity.usecase.post.command.usecase.DeletePostCommand
import com.anycommunity.usecase.post.query.PostQueryBus
import com.anycommunity.usecase.post.query.usecase.GetPostDetailQuery
import com.anycommunity.usecase.post.query.usecase.SearchPostQuery
import com.anycommunity.util.custompage.mapToCustomPage

@Tag(name = "게시글")
@MvcApiController
class PostApi(
    private val postCommandBus: PostCommandBus,
    private val postQueryBus: PostQueryBus,
) {
    @Operation(summary = "게시글 검색")
    @GetMapping("/v1/post/search")
    fun getPosts(
        @Parameter(description = "검색 키워드 (특정 접두사로 시작해야 함)", example = "start_")
        @RequestParam keyword: String?,
        @RequestParam page: Int = 1,
        @RequestParam size: Int = 20,
        @RequestParam postSearchSortType: PostSearchSortType = PostSearchSortType.RECENTLY,
    ) = SearchPostQuery(
        keyword = keyword,
        page = page,
        size = size,
        postSearchSortType = postSearchSortType,
    ).let {
        postQueryBus ask it
    }.mapToCustomPage { PostSearchResponse.from(it) }

    @Operation(summary = "게시글 등록")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/post")
    fun create(@AuthenticationPrincipal user: AuthUser, @RequestBody request: PostCreateRequest) =
        request.toCommand(user.id).let {
            postCommandBus execute it
        }.let { PostResponse.from(it) }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/v1/post/{postId}")
    fun getPostDetail(@PathVariable postId: Long) = GetPostDetailQuery(postId).let {
        postQueryBus.ask(it)
    }.let { PostDetailResponse.from(it) }

    @Operation(summary = "게시글 수정")
    @Secured(AuthRole.USER)
    @PutMapping("/v1/post/{postId}")
    fun updatePost(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable postId: Long,
        @RequestBody request: PostUpdateRequest,
    ) = request.toCommand(user.id, postId).let {
        postCommandBus execute it
    }.let { PostResponse.from(it) }

    @Operation(summary = "게시글 삭제")
    @Secured(AuthRole.USER)
    @DeleteMapping("/v1/post/{postId}")
    fun delete(@AuthenticationPrincipal user: AuthUser, @PathVariable postId: Long) =
        DeletePostCommand(user.id, postId).let {
            postCommandBus execute it
        }
}
