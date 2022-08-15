package com.djphy.myapptemplate.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.djphy.myapptemplate.extension.TAG
import com.djphy.myapptemplate.remote.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    val mRoutineJobs: MutableList<Job> = ArrayList()

    //non local returns not allowed
    protected inline fun <T: Any> addJob(
        crossinline task: suspend () -> Flow<Status<T>>,
        crossinline out: suspend (outFlow: Status<T>) -> Unit
    ) {
        mRoutineJobs.add(viewModelScope.launch {
            val flow = withContext(Dispatchers.IO){
                task()
            }
            flow
                .cancellable()
                .catch { e->
                    e.printStackTrace()
                }
                .onEach {
                    //handle generic error as needed
                    out(it)
                }.launchIn(this)
        })
    }


    fun cancelRequests() {
        mRoutineJobs.forEach {
            it.cancel()
        }
        Log.v(TAG, mRoutineJobs.size.toString() + " request canceled")
        mRoutineJobs.clear()
    }
}