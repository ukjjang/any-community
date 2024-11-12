package com.jinuk.toy.domain.user

import com.jinuk.toy.common.define.user.RawPassword
import com.jinuk.toy.common.define.user.Username

data class UserCredentials(val username: Username, val password: RawPassword)
