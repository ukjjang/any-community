package com.jinuk.toy.externalapi.view.post

import com.jinuk.toy.applicaiton.post.command.adaptor.PostCommandBus
import com.jinuk.toy.externalapi.view.post.request.PostCreateRequest
import com.jinuk.toy.externalapi.view.post.request.toCommand
import com.jinuk.toy.externalapi.view.post.response.toResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/post")
@RestController
class ExternalPostAPI(
    private val postCommandBus: PostCommandBus
) {

    @PostMapping
    fun create(
        @RequestBody request: PostCreateRequest
    ) = postCommandBus.execute(request.toCommand()).toResponse()
}
