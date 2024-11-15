package com.jinuk.toy.mvcapi.post

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
import com.jinuk.toy.applicaiton.post.command.PostCommandBus
import com.jinuk.toy.applicaiton.post.command.usecase.DeletePostCommand
import com.jinuk.toy.applicaiton.post.query.PostQueryBus
import com.jinuk.toy.applicaiton.post.query.usecase.GetPostDetailQuery
import com.jinuk.toy.applicaiton.post.query.usecase.SearchPostQuery
import com.jinuk.toy.common.util.custompage.mapToCustomPage
import com.jinuk.toy.common.value.post.PostSearchSortType
import com.jinuk.toy.mvcapi.global.MvcAPIController
import com.jinuk.toy.mvcapi.global.security.AuthRole
import com.jinuk.toy.mvcapi.global.security.AuthUser
import com.jinuk.toy.mvcapi.post.request.PostCreateRequest
import com.jinuk.toy.mvcapi.post.request.PostUpdateRequest
import com.jinuk.toy.mvcapi.post.request.toCommand
import com.jinuk.toy.mvcapi.post.response.toResponse

@Tag(name = "게시글")
@MvcAPIController
class PostAPI(
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
    ) = postQueryBus.ask(
        SearchPostQuery(
            keyword = keyword,
            page = page,
            size = size,
            postSearchSortType = postSearchSortType,
        ),
    ).mapToCustomPage { it.toResponse() }

    @Operation(summary = "게시글 등록")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/post")
    fun create(@AuthenticationPrincipal user: AuthUser, @RequestBody request: PostCreateRequest) =
        request.toCommand(user.id).let {
            postCommandBus.execute(it)
        }.toResponse()

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/v1/post/{postId}")
    fun getPostDetail(@PathVariable postId: Long) = GetPostDetailQuery(postId).let {
        postQueryBus.ask(it)
    }.toResponse()

    @Operation(summary = "게시글 수정")
    @Secured(AuthRole.USER)
    @PutMapping("/v1/post/{postId}")
    fun updatePost(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable postId: Long,
        @RequestBody request: PostUpdateRequest,
    ) = request.toCommand(user.id, postId).let {
        postCommandBus.execute(it)
    }.toResponse()

    @Operation(summary = "게시글 삭제")
    @Secured(AuthRole.USER)
    @DeleteMapping("/v1/post/{postId}")
    fun delete(@AuthenticationPrincipal user: AuthUser, @PathVariable postId: Long) =
        DeletePostCommand(user.id, postId).let {
            postCommandBus.execute(it)
        }
}
