package com.jinuk.toy.util.jbcrypt

import org.mindrot.jbcrypt.BCrypt

object Jbcrypt {

    fun encrypt(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun verify(password: String, hashed: String): Boolean {
        return BCrypt.checkpw(password, hashed)
    }
}
