package com.githubapiconsumer.model

data class GithubDetails(val name: String, val owner: String, val branches: List<RepoBranchDetails>?)
