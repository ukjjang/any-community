package com.anycommunity.infra.mysql.point.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.anycommunity.infra.mysql.point.entity.PointGameProbabilityEntity

interface PointGameProbabilityEntityRepository : JpaRepository<PointGameProbabilityEntity, Long>
