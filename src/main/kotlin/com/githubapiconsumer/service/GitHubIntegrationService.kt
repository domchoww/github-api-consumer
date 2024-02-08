package com.githubapiconsumer.service

import com.githubapiconsumer.exception.GhUserNotFoundException
import com.githubapiconsumer.model.BranchGhResponse
import com.githubapiconsumer.model.GithubDetails
import com.githubapiconsumer.model.RepoBranchDetails
import com.githubapiconsumer.model.UserRepositoryGhResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class GitHubIntegrationService(private val webClient: WebClient) {
    private val logger: Logger = LoggerFactory.getLogger(GitHubIntegrationService::class.java)

    fun getUserRepositoriesWithBranchesAndCommits(userName: String): Flux<GithubDetails> {
        logger.debug("Fetching repositories for user name: $userName")
        return webClient.get()
            .uri(GITHUB_REPO_PATH, userName)
            .retrieve()
            .onStatus({ it.isSameCodeAs(HttpStatusCode.valueOf(404)) }) { Mono.error(GhUserNotFoundException(userName)) }
            .bodyToFlux(UserRepositoryGhResponse::class.java)
            .filter { !it.fork }
            .flatMap { repo -> toRepoBranchDetails(repo, userName) }
    }

    private fun toRepoBranchDetails(repo: UserRepositoryGhResponse, userName: String) =
        getBranchesForRepository(userName, repo.name)
            .map { branch -> RepoBranchDetails(branch.name, branch.commit.sha) }
            .collectList()
            .map { repoBranchDetails -> GithubDetails(repo.name, repo.owner.login, repoBranchDetails) }

    private fun getBranchesForRepository(userName: String, repoName: String): Flux<BranchGhResponse> {
        logger.debug("Fetching branches for repo: $repoName")
        return webClient.get()
            .uri(GITHUB_BRANCHES_PATCH, userName, repoName)
            .retrieve()
            .bodyToFlux(BranchGhResponse::class.java)
    }

    companion object {
        private const val GITHUB_REPO_PATH = "/users/{userName}/repos"
        private const val GITHUB_BRANCHES_PATCH = "repos/{userName}/{repoName}/branches"
    }
}


