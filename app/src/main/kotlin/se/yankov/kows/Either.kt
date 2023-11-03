package se.yankov.kows

// I'd like to use the Result type, but this thread discusses why it might be a bad idea for domain errors:
// https://stackoverflow.com/a/70847760/488035

sealed interface Either<out R> {
    fun <T> flatMap(f: (R) -> Either<T>): Either<T>

    fun <T> map(f: (R) -> T): Either<T>

    fun mapLeft(f: (Error) -> Error): Either<R>
}

@JvmInline
value class Error(val value: String) {
    override fun toString(): String = value
}

data class Left(val value: Error) : Either<Nothing> {
    override fun <T> flatMap(f: (Nothing) -> Either<T>): Either<T> = this

    override fun <T> map(f: (Nothing) -> T): Either<T> = this

    override fun mapLeft(f: (Error) -> Error): Either<Nothing> = Left(f(value))

    override fun toString(): String = "Left($value)"
}

data class Right<out R>(val value: R) : Either<R> {
    override fun <T> flatMap(f: (R) -> Either<T>): Either<T> = f(value)

    override fun <T> map(f: (R) -> T): Either<T> = Right(f(value))

    override fun mapLeft(f: (Error) -> Error): Either<R> = this

    override fun toString(): String = "Right($value)"
}

object EitherOps {
    fun <R> fromNullable(
        value: R?,
        error: Error,
    ): Either<R> = if (value == null) Left(error) else Right(value)
}

fun String.toError(): Error = Error(this)

fun String.toIntEither(): Either<Int> =
    try {
        Right(toInt())
    } catch (nfe: NumberFormatException) {
        Left("Can not parse the string to an integer".toError())
    }
