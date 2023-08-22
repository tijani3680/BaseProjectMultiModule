package com.base.baseproject.domain.model.exception

sealed class BaseException : RuntimeException() {

    class BadRequestException(
        override val message: String?,
        override val cause: Throwable?
    ) : BaseException()

    class NoInternetException(override val cause: Throwable?) : BaseException()
    class WebsocketsDisconnectException(override val cause: Throwable?) : BaseException()
    class NoBodyFind(override val cause: Throwable?) : BaseException()
    class SuspendAccount(override val message: String?, override val cause: Throwable?) : BaseException()

    class ServerErrorException(
        val code: Int,
        val error: String?,
        override val cause: Throwable?
    ) : BaseException()

    class TimeoutException(override val cause: Throwable?) : BaseException()

    class UnAuthorizeException(override val cause: Throwable?) : BaseException()

    class UnexpectedResponseException(override val cause: Throwable?) : BaseException()

    class UnknownException(
        override val message: String?,
        override val cause: Throwable?
    ) : BaseException()

    class CantCreateRoom(override val message: String?) : BaseException()

    fun exceptionString(): String {
        return when (this) {
            is BadRequestException -> "BadRequestException, $message"
            is CantCreateRoom -> "CantCreateRoom, $message"
            is NoBodyFind -> "NoBodyFind"
            is NoInternetException -> "NoInternetException"
            is ServerErrorException -> "ServerErrorException, $code, $error"
            is SuspendAccount -> "SuspendAccount, $message"
            is TimeoutException -> "TimeoutException"
            is UnAuthorizeException -> "UnAuthorizeException"
            is UnexpectedResponseException -> "UnexpectedResponseException"
            is UnknownException -> "UnknownException, $message"
            is WebsocketsDisconnectException -> "WebsocketsDisconnectException"
        }
    }
}
