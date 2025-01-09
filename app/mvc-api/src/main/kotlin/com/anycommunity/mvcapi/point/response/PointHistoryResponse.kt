package com.anycommunity.mvcapi.point.response

import io.swagger.v3.oas.annotations.media.Schema
import com.anycommunity.definition.point.Point
import com.anycommunity.usecase.point.port.query.model.GetPointHistoryResult

@Schema(description = "포인트 히스토리 조회 응답")
data class PointHistoryResponse(
    @field:Schema(description = "포인트", example = "6100")
    val amount: Point,
    @field:Schema(description = "설명", example = "게시글 작성으로 인한 포인트 지급")
    val description: String,
) {
    companion object {
        fun from(result: GetPointHistoryResult) = with(result) {
            PointHistoryResponse(
                amount = amount,
                description = description,
            )
        }
    }
}
