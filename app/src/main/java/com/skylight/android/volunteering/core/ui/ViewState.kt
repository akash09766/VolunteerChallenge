package com.skylight.android.volunteering.core.ui

/**
 * Describes state of the view at any
 * point of time.
 */
sealed class ViewState<ResultType> {

    /**
     * Describes success state of the UI with
     * [data] shown
     */
    data class Success<ResultType>(
        val data: ResultType
    ) : ViewState<ResultType>()

    /**
     *  Describes error state of the UI
     */
    data class Error<ResultType>(
        val message: String = ""
    ) : ViewState<ResultType>()

    /**
     * Describes loading state of the UI
     */
    data class Loading<ResultType>(
        val data: Boolean = false
    ) : ViewState<ResultType>()

    companion object {
        /**
         * Creates [ViewState] object with [Success] state and [data].
         */
        fun <ResultType> success(data: ResultType): ViewState<ResultType> = Success(data)

        /**
         * Creates [ViewState] object with [Loading] state to notify
         * the UI to showing loading.
         */
        fun <ResultType> loading(status: Boolean): ViewState<ResultType> = Loading(status)

        /**
         * Creates [ViewState] object with [Error] state and [message].
         */
        fun <ResultType> error(message: String): ViewState<ResultType> = Error(message)
    }
}