package com.djphy.myapptemplate.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import com.djphy.myapptemplate.home.HomeState.*
import androidx.lifecycle.viewModelScope
import com.djphy.myapptemplate.base.BaseViewModel
import com.djphy.myapptemplate.MyApp
import com.djphy.myapptemplate.remote.Status
import com.djphy.myapptemplate.repo.MyHomeRepo
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var mMyHomeRepo: MyHomeRepo

    init {
        MyApp.dataComponent().inject(this)
    }

    val mStateObservable: MutableLiveData<HomeState> by lazy {
        MutableLiveData<HomeState>()
    }

    private var mState: HomeState = IdleState
        set(value) {
            field = value
            publishState(value)
        }

    private fun publishState(state: HomeState) {
        //always publish on UI thread
        viewModelScope.launch(Dispatchers.Main) {
            mStateObservable.setValue(state)
        }
    }

    //not to be called from within
    fun nextState(state: HomeState) {
        when (state) {
            HomeFragmentState.FetchUserLaunchListState -> {
                mState = LoadingState(true)
                getUserLaunchList(10, 10)
            }

            is HomeFragmentState.ArticleItemClickState -> {
                mState = StartWebViewState(
                    url = state.data.article_link,
                    title = state.title
                )
            }

            else -> {
                mState = state
            }
        }
    }

    private fun getUserLaunchList(limit: Int, launchLimit: Int) {
        addJob({
            mMyHomeRepo.getUserAndLaunchesList(
                limit, launchLimit
            )
        }, {
            when(it){
                is Status.OnSuccess -> {
                    it.response.also { list ->
                        mState = HomeFragmentState.FetchUserLaunchListResponseState(
                            isSuccess = true,
                            data = list
                        )
                    }
                }

                is Status.OnFailed -> {
                    mState = HomeFragmentState.FetchUserLaunchListResponseState(
                        isSuccess = false,
                        message = it.throwable.message
                    )
                }
            }
        })
    }
}