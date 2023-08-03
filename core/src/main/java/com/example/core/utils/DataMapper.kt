package com.example.core.utils

import com.example.core.data.source.local.entity.GithubUserEntity
import com.example.core.data.source.remote.response.GithubUserReposResponse
import com.example.core.data.source.remote.response.GithubUserResponse
import com.example.core.domain.model.GithubUser
import com.example.core.domain.model.GithubUserRepo

object DataMapper {

    fun mapEntitiesToDomain(input: List<GithubUserEntity>) : List<GithubUser>{
        val githubUsersList = ArrayList<GithubUser>()
        input.map {
            val githubUser = GithubUser(
                id = it.id,
                gistsUrl = it.gistsUrl ?: "",
                url = it.url ?: "",
                avatarUrl = it.avatarUrl ?: "",
                eventsUrl = it.eventsUrl ?: "",
                receivedEventsUrl = it.receivedEventsUrl ?: "",
                reposUrl = it.reposUrl ?: "",
                starredUrl = it.starredUrl ?: "",
                login = it.login ?: "",
                nodeId = it.nodeId ?: "",
                gravatarId = it.gravatarId ?: "",
                followersUrl = it.followersUrl ?: "",
                followingUrl = it.followingUrl ?: "",
                subscriptionsUrl = it.subscriptionsUrl ?: "",
                organizationsUrl = it.organizationsUrl ?: "",
                type = it.type ?: "",
                siteAdmin = it.siteAdmin ?: false,
                isFavorite = it.isFavorite,
                htmlUrl = it.htmlUrl ?: "",
                name = it.name ?: ""
            )
            githubUsersList.add(githubUser)
        }
        return githubUsersList
    }

    fun mapResponseToDomains(input: List<GithubUserResponse>) : List<GithubUser>{
        val githubUsersList = ArrayList<GithubUser>()
        input.map {
            val githubUser = GithubUser(
                id = it.id ?: 0,
                gistsUrl = it.gistsUrl ?: "",
                url = it.url ?: "",
                avatarUrl = it.avatarUrl ?: "",
                eventsUrl = it.eventsUrl ?: "",
                receivedEventsUrl = it.receivedEventsUrl ?: "",
                reposUrl = it.reposUrl ?: "",
                starredUrl = it.starredUrl ?: "",
                login = it.login ?: "",
                nodeId = it.nodeId ?: "",
                gravatarId = it.gravatarId ?: "",
                followersUrl = it.followersUrl ?: "",
                followingUrl = it.followingUrl ?: "",
                subscriptionsUrl = it.subscriptionsUrl ?: "",
                organizationsUrl = it.organizationsUrl ?: "",
                type = it.type ?: "",
                siteAdmin = it.siteAdmin ?: false,
                isFavorite = false,
                htmlUrl = it.htmlUrl ?: "",
                name = it.name ?: ""
            )
            githubUsersList.add(githubUser)
        }
        return githubUsersList
    }

    fun mapResponseToDomain(input: GithubUserResponse, isFavorite: Boolean = false) : GithubUser = GithubUser(
        id = input.id ?: 0,
        gistsUrl = input.gistsUrl ?: "",
        url = input.url ?: "",
        avatarUrl = input.avatarUrl ?: "",
        eventsUrl = input.eventsUrl ?: "",
        receivedEventsUrl = input.receivedEventsUrl ?: "",
        reposUrl = input.reposUrl ?: "",
        starredUrl = input.starredUrl ?: "",
        login = input.login ?: "",
        nodeId = input.nodeId ?: "",
        gravatarId = input.gravatarId ?: "",
        followersUrl = input.followersUrl ?: "",
        followingUrl = input.followingUrl ?: "",
        subscriptionsUrl = input.subscriptionsUrl ?: "",
        organizationsUrl = input.organizationsUrl ?: "",
        type = input.type ?: "",
        siteAdmin = input.siteAdmin ?: false,
        isFavorite = isFavorite,
        htmlUrl = input.htmlUrl ?: "",
        name = input.name ?: ""
    )

    fun mapDomainToEntity(input: GithubUser) : GithubUserEntity = GithubUserEntity(
        id = input.id,
        gistsUrl = input.gistsUrl,
        url = input.url,
        avatarUrl = input.avatarUrl,
        eventsUrl = input.eventsUrl,
        receivedEventsUrl = input.receivedEventsUrl,
        reposUrl = input.reposUrl,
        starredUrl = input.starredUrl,
        login = input.login,
        nodeId = input.nodeId,
        gravatarId = input.gravatarId,
        followersUrl = input.followersUrl,
        followingUrl = input.followingUrl,
        subscriptionsUrl = input.subscriptionsUrl,
        organizationsUrl = input.organizationsUrl,
        type = input.type,
        siteAdmin = input.siteAdmin,
        isFavorite = input.isFavorite,
        htmlUrl = input.htmlUrl,
        name = input.name,
    )

    fun mapReposResponseToDomains(input: List<GithubUserReposResponse>) : List<GithubUserRepo>{
        val repos = ArrayList<GithubUserRepo>()
        input.map {
            val repo = GithubUserRepo(
                language = it.language ?: "",
                id = it.id ?: 0,
                name = it.name ?: "",
                description = it.description?: "",
            )
            repos.add(repo)
        }
        return repos
    }
}