package jwt

import com.jinuk.toy.util.jwt.JwtTokenProvider
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class JwtTokenProviderTest : DescribeSpec({
    val secretKey = "bC94mIxS7qJ5Zm8wFf4H9jL3tO0U1iXnA2KdP7L6eE8V9vA"
    val jwtTokenProvider = JwtTokenProvider(secretKey)

    describe("JwtTokenProvider 테스트") {
        it("토큰 생성 하고 토큰에서 추출한 사용자 이름이 일치하는지") {
            val username = "testuser"
            val token = jwtTokenProvider.createToken(username)

            token shouldNotBe null
            jwtTokenProvider.resolveToken("Bearer $token") shouldBe username
        }
    }
})
