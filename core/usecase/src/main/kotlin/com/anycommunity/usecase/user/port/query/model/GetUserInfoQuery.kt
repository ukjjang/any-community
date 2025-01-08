package com.anycommunity.usecase.user.port.query.model

import com.anycommunity.definition.user.Username

data class GetUserInfoQuery(
    val username: Username,
)
