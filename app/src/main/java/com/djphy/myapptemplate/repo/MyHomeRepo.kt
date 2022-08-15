package com.djphy.myapptemplate.repo

import com.apollographql.apollo3.ApolloClient
import com.diphy.graphql.UsersListQuery
import com.djphy.myapptemplate.MyApp
import com.djphy.myapptemplate.remote.Status
import com.djphy.myapptemplate.remote.performNetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyHomeRepo: MyHomeRepoContract {

    @Inject
    lateinit var mApiService: ApolloClient

    init {
        MyApp.appComponent().inject(this)
    }

    override suspend fun getUserAndLaunchesList(userLimit: Int, launchLimit: Int): Flow<Status<UsersListQuery.Data>> {
        val apiCall = mApiService.query(UsersListQuery(userLimit, launchLimit)).execute()
        return performNetworkCall {apiCall}
    }

}