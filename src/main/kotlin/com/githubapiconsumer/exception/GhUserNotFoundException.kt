package com.githubapiconsumer.exception

data class GhUserNotFoundException(val userName: String) : RuntimeException(userName)
