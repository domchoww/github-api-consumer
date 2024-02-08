package com.githubapiconsumer

import org.intellij.lang.annotations.Language

@Language("json")
val repositoryEndpointResponse: String = """
            [
                {
                    "id": 129610885,
                    "name": "SpringApplication",
                    "owner": {
                        "login": "someOwner"
                    },
                    "fork": false
                },
                {
                    "id": 129610886,
                    "name": "Some Forked",
                    "owner": {
                        "login": "tester"
                    },
                    "fork": true
                }
            ]
        """.trimIndent()

@Language("json")
val branchesEndpointResponse: String = """
            [
                {
                    "name": "master",
                    "commit": {
                        "sha": "1234",
                        "url": "https://api.github.com/repos/1234"
                    },
                    "protected": false
                }
            ]
        """.trimIndent()