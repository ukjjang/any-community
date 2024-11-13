package com.jinuk.toy.domain.user

import com.jinuk.toy.common.value.user.RawPassword
import com.jinuk.toy.common.value.user.Username

data class UserCredentials(val username: Username, val password: RawPassword)
