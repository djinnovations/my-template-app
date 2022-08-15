package com.djphy.myapptemplate.repo

import com.diphy.graphql.UsersListQuery
import com.djphy.myapptemplate.remote.Status
import kotlinx.coroutines.flow.Flow

interface MyHomeRepoContract {

    suspend fun getUserAndLaunchesList(userLimit: Int, launchLimit: Int): Flow<Status<UsersListQuery.Data>>
}