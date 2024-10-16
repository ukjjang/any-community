package com.jinuk.toy.infra.rdb.like.jpa

import com.jinuk.toy.infra.rdb.like.entity.LikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LikeEntityRepository : JpaRepository<LikeEntity, Long>
