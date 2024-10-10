package com.jinuk.toy.applicaiton.post.query.adaptor

import com.jinuk.toy.applicaiton.post.query.port.PostDetailQueryApplication
import com.jinuk.toy.domain.post.Post
import org.springframework.stereotype.Service

sealed interface PostQueryBus {
    infix fun query(id: Long): Post
}

@Service
internal class PostQueryBusImpl(
    private val postDetailQueryApplication: PostDetailQueryApplication
) : PostQueryBus {

    override fun query(id: Long) = postDetailQueryApplication(id)
}
