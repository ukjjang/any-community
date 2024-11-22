package com.anycommunity.util.jbcrypt

import org.mindrot.jbcrypt.BCrypt
import com.anycommunity.definition.user.RawPassword

object Jbcrypt {
    fun encrypt(password: RawPassword): String = BCrypt.hashpw(password.value, BCrypt.gensalt())

    fun verify(password: RawPassword, hashed: String) = BCrypt.checkpw(password.value, hashed)
}
