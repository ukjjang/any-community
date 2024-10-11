package application.post.query

import application.IntegrationTest
import com.jinuk.toy.applicaiton.post.query.PostDetailQueryApplication
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PostQueryApplicationTest(
    private val postDetailQueryApplication: PostDetailQueryApplication,
    private val postRepository: PostRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("post query") {
            context("post exists") {
                val exist = postRepository.save(PostFixture.create())

                it("query success") {
                    val post = postDetailQueryApplication(exist.id!!)
                    post shouldBe exist
                }

                it("query fail") {
                    shouldThrow<NoSuchElementException> {
                        postDetailQueryApplication(exist.id!! + 1)
                    }
                }
            }
        }
    },
)

