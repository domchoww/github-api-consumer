package com.githubapiconsumer.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponseDto(
    val status: Int,
    @JsonProperty("Message")
    val message: String,
)