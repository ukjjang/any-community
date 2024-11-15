package com.jinuk.toy.applicaiton.comment.query.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.common.value.like.LikeType
import com.jinuk.toy.domain.comment.CommentFixture
import com.jinuk.toy.domain.like.LikeFixture
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.user.UserFixture

class GetCommentPageUsecaseTest(
    private val getCommentPageUsecase: GetCommentPageUsecase,
    private val postFixture: PostFixture,
    private val userFixture: UserFixture,
    private val commentFixture: CommentFixture,
    private val likeFixture: LikeFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("게시글의 댓글 조회 유스케이스") {
            it("조회 성공") {
                val post = postFixture.persist()
                val viewer = userFixture.persist()
                val writer = userFixture.persist()

                commentFixture.persist(postId = post.id, parentCommentId = null)
                commentFixture.persist(postId = post.id, parentCommentId = null)
                commentFixture.persist(userId = writer.id, postId = post.id, parentCommentId = null)
                val comment1 = commentFixture.persist(userId = writer.id, postId = post.id, parentCommentId = null)

                val comment2 = commentFixture.persist(userId = writer.id, postId = post.id, parentCommentId = null)
                val child1 =
                    commentFixture.persist(
                        userId = writer.id,
                        postId = post.id,
                        parentCommentId = comment2.id,
                    )
                val child2 =
                    commentFixture.persist(
                        userId = writer.id,
                        postId = post.id,
                        parentCommentId = comment2.id,
                    )
                val child3 =
                    commentFixture.persist(
                        userId = writer.id,
                        postId = post.id,
                        parentCommentId = comment2.id,
                    )
                val child4 =
                    commentFixture.persist(
                        userId = writer.id,
                        postId = post.id,
                        parentCommentId = child3.id,
                    )
                val child5 =
                    commentFixture.persist(
                        userId = writer.id,
                        postId = post.id,
                        parentCommentId = child3.id,
                    )
                val child6 =
                    commentFixture.persist(
                        userId = writer.id,
                        postId = post.id,
                        parentCommentId = child3.id,
                    )
                val child7 =
                    commentFixture.persist(
                        userId = writer.id,
                        postId = post.id,
                        parentCommentId = child5.id,
                    )

                likeFixture.persist(
                    userId = viewer.id,
                    targetType = LikeType.COMMENT,
                    targetId = child3.id.toString(),
                )
                likeFixture.persist(
                    userId = viewer.id,
                    targetType = LikeType.COMMENT,
                    targetId = comment2.id.toString(),
                )

                val query =
                    GetCommentPageQuery(
                        postId = post.id,
                        viewerId = viewer.id,
                        page = 1,
                        size = 2,
                    )

                val result = getCommentPageUsecase(query)

                result.size shouldBe 2

                val resultComment1 = result.content[0]
                resultComment1.id shouldBe comment2.id
                resultComment1.content shouldBe comment2.content
                resultComment1.isViewerLike shouldBe true

                resultComment1.children.size shouldBe 3
                resultComment1.children[0].id shouldBe child3.id
                resultComment1.children[0].isViewerLike shouldBe true

                resultComment1.children[0].children.size shouldBe 3
                resultComment1.children[0].children[0].id shouldBe child6.id
                resultComment1.children[0].children[1].id shouldBe child5.id
                resultComment1.children[0].children[2].id shouldBe child4.id

                resultComment1.children[1].id shouldBe child2.id
                resultComment1.children[2].id shouldBe child1.id

                resultComment1.children[0].children[1].children.size shouldBe 1
                resultComment1.children[0].children[1].children[0].id shouldBe child7.id

                val resultComment2 = result.content[1]
                resultComment2.id shouldBe comment1.id
                resultComment2.content shouldBe comment1.content
                resultComment2.isViewerLike shouldBe false
                resultComment2.children.size shouldBe 0
            }
        }
    },
)
