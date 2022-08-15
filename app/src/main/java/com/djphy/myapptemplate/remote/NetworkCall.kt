package com.djphy.myapptemplate.remote

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.io.IOException


typealias NetworkAPIInvoke<T> = suspend () -> ApolloResponse<T>

suspend fun <T : Operation.Data> performNetworkCall(
    messageInCaseOfError: String = "Network error",
    allowRetries: Boolean = false,
    numberOfRetries: Int = 2,
    networkApiCall: NetworkAPIInvoke<T>
): Flow<Status<T>> {
    var delayDuration = 1000L
    val delayFactor = 2
    return flow {
        try {
            emit(Status.Loading)
            val response = networkApiCall()
            if (response.hasErrors().not()) {
                response.data?.let { emit(Status.OnSuccess(it)) } ?: emit(
                    Status.OnFailed(
                        IOException("API call successful but empty response body")
                    )
                )
                return@flow
            }
            val error = response.errors?.get(0)
            emit(Status.OnFailed(IOException(error.toString() ?: messageInCaseOfError)))
            return@flow
        } catch (e: Exception) {
            val error = e.message
            emit(Status.OnFailed(IOException(error.toString())))
            return@flow
        }
    }.catch { e ->
        emit(Status.OnFailed(IOException("Exception during network API call: ${e.message}")))
        return@catch
    }.retryWhen { cause, attempt ->
        if (!allowRetries || attempt > numberOfRetries || cause !is IOException) return@retryWhen false
        delay(delayDuration)
        delayDuration *= delayFactor
        return@retryWhen true
    }.flowOn(Dispatchers.IO)
}
