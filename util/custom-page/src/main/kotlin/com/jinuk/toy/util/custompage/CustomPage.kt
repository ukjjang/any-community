package com.jinuk.toy.util.custompage

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@JsonIgnoreProperties(ignoreUnknown = true, value = ["pageable"])
class CustomPage<T> : PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    constructor(
        @JsonProperty("content") content: List<T>,
        @JsonProperty("number") page: Int,
        @JsonProperty("size") size: Int,
        @JsonProperty("totalElements") total: Long,
    ) : super(content, PageRequest.of(page, size), total)

    constructor(page: Page<T>) : super(page.content, page.pageable, page.totalElements)
    constructor(content: List<T>, pageable: Pageable, total: Long) : super(content, pageable, total)
}

fun <T> Page<T>.toCustomPage() = CustomPage(this)
