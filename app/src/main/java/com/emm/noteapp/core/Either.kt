package com.emm.noteapp.core

sealed class Either<out T> {
    data class Success<out T>(val data: T) : Either<T>()
    data class Error(val error: Throwable? = null) : Either<Nothing>()
}

inline fun <T, X> Either<T>.mapper(mapper: (T) -> X): Either<X> {
    return when (this) {
        is Either.Error -> Either.Error(this.error)
        is Either.Success -> Either.Success(mapper(this.data))
    }
}
