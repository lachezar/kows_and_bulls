package se.yankov.kows

// TODO: Can we have an Array with 4 elements as a type? Dependent types?
class Candidate(val digits: IntArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Candidate) return false
        return digits.contentEquals(other.digits)
    }

    override fun toString(): String {
        return digits.joinToString(separator = "")
    }

    fun compare(other: Candidate): Answer {
        val bulls: Bulls = Bulls(digits.zip(other.digits).fold(0) { acc, (a, b) -> acc + if (a == b) 1 else 0 })
        val kows: Kows = Kows(digits.size - (digits.toSet() - other.digits.toSet()).size - bulls.value)
        return Answer(kows, bulls)
    }

    fun validate(): Either<Candidate> =
        if (digits.toSet().size != digits.size) {
            Left("The number candidate must not contain repeating digits".toError())
        } else if (digits[0] == 0) {
            Left("The number candidate must not start with 0".toError())
        } else {
            Right(this)
        }

    companion object {
        val all: Collection<Candidate> by lazy { permutations(listOf()) }

        fun parse(input: String): Either<Candidate> =
            try {
                Right(input.map(Char::digitToInt).toIntArray()).flatMap {
                    if (it.size == 4) {
                        Right(Candidate(it))
                    } else {
                        Left("The input must be integer with 4 digits".toError())
                    }
                }
            } catch (nfe: NumberFormatException) {
                Left("The input must be integer".toError())
            }

        internal fun permutations(state: List<Int>): Collection<Candidate> =
            if (state.size == 4) {
                listOf(Candidate(state.toIntArray()))
            } else {
                val range: IntRange = if (state.isEmpty()) 1..9 else 0..9
                range.fold(listOf()) { acc, digit ->
                    if (state.contains(digit)) acc else acc.plus(permutations(state.plus(digit)))
                }
            }
    }
}
