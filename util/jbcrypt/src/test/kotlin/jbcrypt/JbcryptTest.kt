package jbcrypt

import com.jinuk.toy.util.jbcrypt.Jbcrypt
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class JbcryptTest : DescribeSpec({

    describe("Jbcrypt 암호화/검증 테스트") {

        it("비밀번호 해시화 및 검증 성공 케이스") {
            val originalPassword = "successPassword"
            val hashedPassword = Jbcrypt.encrypt(originalPassword)
            hashedPassword shouldNotBe originalPassword
            Jbcrypt.verify(originalPassword, hashedPassword) shouldBe true
        }

        it("잘못된 비밀번호 검증 실패 케이스") {
            val originalPassword = "successPassword"
            val hashedPassword = Jbcrypt.encrypt(originalPassword)

            val wrongPassword = "wrongPassword"
            Jbcrypt.verify(wrongPassword, hashedPassword) shouldBe false
        }
    }
})
