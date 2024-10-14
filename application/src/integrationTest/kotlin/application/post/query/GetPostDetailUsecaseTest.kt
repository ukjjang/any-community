package application.post.query

import application.IntegrationTest
import com.jinuk.toy.applicaiton.post.query.GetPostDetailQuery
import com.jinuk.toy.applicaiton.post.usecase.GetPostDetailUsecase
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GetPostDetailUsecaseTest(
    private val getPostDetailUsecase: GetPostDetailUsecase,
    private val postRepository: PostRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("post query") {
            context("post exists") {
                val exist = postRepository.save(PostFixture.create())

                it("query success") {
                    val post = getPostDetailUsecase(GetPostDetailQuery(exist.id!!))
                    post shouldBe exist
                }

                it("query fail") {
                    shouldThrow<NoSuchElementException> {
                        getPostDetailUsecase(GetPostDetailQuery(exist.id!! + 1))
                    }
                }
            }
        }
    },
)

