package com.example.civilservantapp.model

sealed class NetWorkViewState<T> {
    class Loading<T>(var loading: Boolean): NetWorkViewState<T>()
    class Success<T>(val item: T): NetWorkViewState<T>()
    class Error<T>(val errormessage: Throwable): NetWorkViewState<T>()
    companion object{
        fun <T> loading(isLoading: Boolean): NetWorkViewState<T> = Loading(isLoading)

        fun <T> success(data: T): NetWorkViewState<T> = Success(data)

        fun <T> error(e: Throwable): NetWorkViewState<T> = Error(e)
    }
}