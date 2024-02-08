package com.githubapiconsumer.controller

import com.githubapiconsumer.service.GitHubIntegrationService
import com.githubapiconsumer.exception.UnsupportedContentTypeException
import com.githubapiconsumer.model.GithubDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class GitHubIntegrationController(private val gitHubRepositoryService: GitHubIntegrationService) {
    @GetMapping("/user/{user_name}/repositories")
    fun getUserRepositories(
        @PathVariable("user_name") userName: String,
        @RequestHeader("Accept") acceptHeader: String,
    ): Flux<GithubDetails> {
        if(acceptHeader.contains("application/xml")) throw UnsupportedContentTypeException()
        return gitHubRepositoryService.getUserRepositoriesWithBranchesAndCommits(userName)
    }
}