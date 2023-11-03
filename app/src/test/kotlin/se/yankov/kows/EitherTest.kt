package se.yankov.kows

import kotlin.test.Test
import kotlin.test.assertEquals

class EitherTest {
    @Test
    fun flatMap() {
        val a: Either<Int> = Left("".toError())
        assertEquals(a, a.flatMap { Right(42) })

        val b: Either<Int> = Right(1)
        assertEquals(b.flatMap { Left("".toError()) }, Left("".toError()))

        val c: Either<Int> = Right(1)
        assertEquals(c.flatMap { Right(it + 5) }, Right(6))
    }

    @Test
    fun map() {
        val a: Either<Int> = Left("".toError())
        assertEquals(a, a.map { it + 2 })

        val b: Either<Int> = Right(1)
        assertEquals(b.map { it + 2 }, Right(3))
    }

    @Test
    fun mapLeft() {
        val a: Either<Int> = Right(1)
        assertEquals(a.mapLeft { "other error".toError() }, Right(1))

        val b: Either<Int> = Left("".toError())
        assertEquals(b.mapLeft { "other error".toError() }, Left("other error".toError()))
    }
}
