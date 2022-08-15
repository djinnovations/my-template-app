package com.djphy.myapptemplate.home

import android.os.Parcelable
import com.diphy.graphql.UsersListQuery
import com.djphy.myapptemplate.home.model.PopularListResponse
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

sealed class HomeState : Parcelable {

    @Parcelize
    object IdleState : HomeState(), Parcelable

    @Parcelize
    object InitState : HomeState(), Parcelable

    @Parcelize
    data class LoadingState(val isLoading:Boolean) : HomeState(), Parcelable

    @Parcelize
    data class FailedState(
        val message: String? = null
    ) : HomeState(), Parcelable

    @Parcelize
    data class StartWebViewState(
        val url: String? = null,
        val title: String? = ""
    ) : HomeState(), Parcelable

    sealed class HomeFragmentState : HomeState() {
        @Parcelize
        object InitState : HomeFragmentState(), Parcelable

        @Parcelize
        object FetchUserLaunchListState : HomeFragmentState(), Parcelable

        @Parcelize
        data class FetchUserLaunchListResponseState(
            val message: String? = null,
            val data: @RawValue UsersListQuery.Data? = null,
            val code: Int? = null,
            val isSuccess: Boolean
        ) : HomeFragmentState(), Parcelable

        @Parcelize
        data class ArticleItemClickState(
            val data: @RawValue UsersListQuery.Links, val title: String
        ) : HomeFragmentState(), Parcelable

        @Parcelize
        data class InitLoadFailedState(
            val message: String? = null
        ) : HomeState(), Parcelable
    }
}