package ru.roadreport.android.utils

data class Resource<out T>(val status: Status, val data: T?) {

    enum class Status {
        SUCCESS,
        LOADING,
        ERROR,
        NETWORK_ERROR;
    }

    fun <R> map( block: (T?) -> R? ): Resource<R> {
        return Resource(status, block(data))
    }

    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return error(data)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data)
        }

        fun <T> networkError(data: T? = null): Resource<T> {
            return Resource(Status.NETWORK_ERROR, null)
        }

    }
}