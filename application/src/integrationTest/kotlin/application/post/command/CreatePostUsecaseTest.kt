package application.post.command

import application.IntegrationTest
import com.jinuk.toy.applicaiton.post.command.CreatePostCommand
import com.jinuk.toy.applicaiton.post.usecase.CreatePostUsecase
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.domain.post.value.PostTitle
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class CreatePostUsecaseTest(
    private val createPostUsecase: CreatePostUsecase,
    private val postRepository: PostRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("post create") {
            context("post exists title") {
                val existsTitle = PostTitle("exists")
                val exits = PostFixture.create(title = existsTitle)
                postRepository.save(exits)

                it("create success") {
                    val title = PostTitle("title")
                    val command = CreatePostCommand(1, title, "content")

                    val post = createPostUsecase(command)
                    val postEntity = postRepository.findById(post.id!!)

                    post shouldBe postEntity
                    post.userId shouldBe 1
                    post.title shouldBe title
                    post.content shouldBe "content"
                }

                it("create fail - exists title") {
                    val command = CreatePostCommand(1, existsTitle, "content")
                    shouldThrow<IllegalArgumentException> {
                        createPostUsecase(command)
                    }
                }
            }
        }
    },
)
