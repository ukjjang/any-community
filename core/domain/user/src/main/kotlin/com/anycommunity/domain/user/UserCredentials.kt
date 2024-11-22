package com.anycommunity.domain.user

import com.anycommunity.definition.user.RawPassword
import com.anycommunity.definition.user.Username

data class UserCredentials(val username: Username, val password: RawPassword)
