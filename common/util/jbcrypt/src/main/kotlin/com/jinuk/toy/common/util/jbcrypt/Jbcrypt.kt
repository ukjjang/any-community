package com.jinuk.toy.common.util.jbcrypt

import org.mindrot.jbcrypt.BCrypt
import com.jinuk.toy.common.value.user.RawPassword

object Jbcrypt {
    fun encrypt(password: RawPassword): String {
        return BCrypt.hashpw(password.value, BCrypt.gensalt())
    }

    fun verify(
        password: RawPassword,
        hashed: String,
    ): Boolean {
        return BCrypt.checkpw(password.value, hashed)
    }
}
