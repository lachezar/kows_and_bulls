package se.yankov.kows

@JvmInline
value class Kows(val value: Int) {
    // TODO: Is there a way to have automatic derivation?
    operator fun compareTo(kows: Kows): Int = value.compareTo(kows.value)
}

@JvmInline
value class Bulls(val value: Int) {
    // TODO: Is there a way to have automatic derivation?
    operator fun compareTo(bulls: Bulls): Int = value.compareTo(bulls.value)
}

data class Answer(val kows: Kows, val bulls: Bulls) {
    fun isWin(): Boolean = bulls == Bulls(4)

    fun validate(): Either<Answer> =
        if (kows < Kows(0) || kows > Kows(4)) {
            Left("Kows must be between 0 and 4".toError())
        } else if (bulls < Bulls(0) || bulls > Bulls(4)) {
            Left("Bulls must be between 0 and 4".toError())
        } else if (bulls.value + kows.value > 4) {
            Left("Sum of kows and bulls must be between 0 and 4".toError())
        } else {
            Right(this)
        }

    companion object {
        fun parse(
            kowsInput: String,
            bullsInput: String,
        ): Either<Answer> =
            kowsInput.toIntEither().mapLeft { "The kows must be integer".toError() }.flatMap { k ->
                bullsInput.toIntEither()
                    .mapLeft { "The bulls must be integer".toError() }
                    .map { b -> Answer(Kows(k), Bulls(b)) }
            }
    }
}
