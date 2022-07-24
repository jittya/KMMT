package com.kmmt.common.functional


/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Failure] or [Success].
 * FP Convention dictates that [Failure] is used for "failure"
 * and [Success] is used for "success".
 *
 * @see Failure
 * @see Success
 */
sealed class Either<out SuccessType, out FailureType> {
    /** * Represents the failure side of [Either] class which by convention is a "Failure". */
    data class Failure<out FailureType>(val a: FailureType) : Either<Nothing, FailureType>()

    /** * Represents the success side of [Either] class which by convention is a "Success". */
    data class Success<out SuccessType>(val b: SuccessType) : Either<SuccessType, Nothing>()

    val isSuccess get() = this is Success<SuccessType>
    val isFailure get() = this is Failure<FailureType>

    fun <FailureType> failure(a: FailureType) = Failure(a)
    fun <SuccessType> success(b: SuccessType) = Success(b)

    fun getSuccessOrNull(): SuccessType? {
        return if (this is Success) {
            b
        } else {
            null
        }
    }

    fun getFailureOrNull(): FailureType? {
        return if (this is Failure) {
            a
        } else {
            null
        }
    }

    fun either(fnR: (FailureType) -> Unit, fnL: (SuccessType) -> Unit): Unit =
        when (this) {
            is Failure -> fnR(a)
            is Success -> fnL(b)
        }

    suspend fun eitherAsync(fnR: suspend (FailureType) -> Unit, fnL: suspend (SuccessType) -> Unit): Unit =
        when (this) {
            is Failure -> fnR(a)
            is Success -> fnL(b)
        }
}

// Credits to Alex Hart -> https://proandroiddev.com/kotlins-nothing-type-946de7d464fb
// Composes 2 functions
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, L, R> Either<L, R>.flatMap(fn: (L) -> Either<T, R>): Either<T, R> =
    when (this) {
        is Either.Failure -> Either.Failure(a)
        is Either.Success -> fn(b)
    }

fun <T, L, R> Either<L, R>.map(fn: (L) -> (T)): Either<T, R> = this.flatMap(fn.c(::success))
