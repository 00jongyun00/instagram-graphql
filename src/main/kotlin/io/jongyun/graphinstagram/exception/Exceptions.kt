package io.jongyun.graphinstagram.exception

open class BusinessException(
    open val errorCode: ErrorCode,
    override val message: String,
    open val throwable: Throwable? = null,
) : RuntimeException(message, throwable)