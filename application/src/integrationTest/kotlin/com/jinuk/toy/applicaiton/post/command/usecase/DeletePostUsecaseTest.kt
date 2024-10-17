package com.jinuk.toy.applicaiton.post.command.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.comment.CommentFixture
import com.jinuk.toy.domain.comment.jpa.CommentRepository
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

internal class DeletePostUsecaseTest(
    private val deletePostUsecase: DeletePostUsecase,
    private val postRepository: PostRepository,
    private val postFixture: PostFixture,
    private val commentFixture: CommentFixture,
    private val commentRepository: CommentRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("게시글 삭제 유스케이스") {
            it("삭제 성공") {
                val exits = postFixture.persist()

                commentFixture.persist(postId = exits.id)
                commentFixture.persist(postId = exits.id)
                val anotherPostComment = commentFixture.persist()

                val command = DeletePostCommand(exits.userId, exits.id)
                deletePostUsecase(command)

                postRepository.findById(exits.id) shouldBe null
                commentRepository.findByPostId(command.id) shouldBe emptyList()
                commentRepository.findById(anotherPostComment.id) shouldNotBe null
            }

            it("삭제 실패 - 작성자가 아닌 유저") {
                val exits = postFixture.persist()
                val command = DeletePostCommand(faker.randomLong(), exits.id)
                shouldThrow<IllegalArgumentException> {
                    deletePostUsecase(command)
                }
            }
        }
    },
)
