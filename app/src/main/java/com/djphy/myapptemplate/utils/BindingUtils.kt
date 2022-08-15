package com.djphy.myapptemplate.utils

import com.djphy.myapptemplate.home.HomeState

class BindingUtils {

    companion object {
        @JvmStatic
        fun isFailed(state: HomeState): Boolean {
            return state is HomeState.HomeFragmentState.InitLoadFailedState
        }

        @JvmStatic
        fun isLoading(state: HomeState): Boolean {
            return if (isLoadingState(state))
                (state as HomeState.LoadingState).isLoading
            else false
        }

        private fun isLoadingState(state: HomeState): Boolean {
            return state is HomeState.LoadingState
        }

        @JvmStatic
        fun getFailureMessage(state: HomeState): String {
            return if (isFailed(state)) {
                (state as HomeState.HomeFragmentState.InitLoadFailedState).message?: ""
            } else ""
        }
    }
}