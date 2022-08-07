package io.jongyun.graphinstagram.util

import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import org.springframework.security.core.context.SecurityContextHolder

fun getAuthName(): Long {
    val name = SecurityContextHolder.getContext().authentication?.name
    return name?.toLongOrNull() ?: throw BusinessException(
        ErrorCode.YOU_DO_NOT_HAVE_PERMISSION,
        "권한을 확인해주세요"
    )
}