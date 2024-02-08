package com.githubapiconsumer.model

data class UserRepositoryGhResponse(val name: String, val owner: OwnerGhResponse, val fork: Boolean)
