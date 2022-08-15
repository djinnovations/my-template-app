package com.djphy.myapptemplate.home.model

import android.os.Parcelable
import com.diphy.graphql.UsersListQuery
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class UserAndLaunches(
    var user: @RawValue UsersListQuery.User,
    var launches: @RawValue UsersListQuery.Launch?
): Parcelable
