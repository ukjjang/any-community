package com.jinuk.toy.common.util.custompage

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@JsonIgnoreProperties(value = ["pageable", "last", "first", "numberOfElements", "sort", "empty"])
class CustomPage<T> : PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    constructor(
        @JsonProperty("content") content: List<T>,
        @JsonProperty("number") page: Int,
        @JsonProperty("size") size: Int,
        @JsonProperty("totalElements") total: Long,
    ) : super(content, PageRequest.of(page, size), total)

    constructor(content: List<T>, pageable: Pageable, total: Long) : super(content, pageable, total)
    constructor(page: Page<T>) : super(page.content, page.pageable, page.totalElements)
}

fun <T> Page<T>.toCustomPage() = CustomPage(this)

fun <T, R> Page<T>.toCustomPage(content: List<R>) = CustomPage(
    content = content,
    pageable = this.pageable,
    total = this.totalElements,
)

fun <T, R> CustomPage<T>.mapToCustomPage(transform: (T) -> R): CustomPage<R> = CustomPage(
    content = content.map(transform),
    pageable = pageable,
    total = totalElements,
)
