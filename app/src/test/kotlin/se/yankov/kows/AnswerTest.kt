package se.yankov.kows

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class AnswerTest {
    @Test
    fun answersEquality() {
        val a = Answer(Kows(1), Bulls(2))
        val b = Answer(Kows(1), Bulls(2))
        val c = Answer(Kows(0), Bulls(0))

        assertEquals(a, a)
        assertEquals(a, b)
        assertNotEquals(a, c)
        assertNotEquals(c, b)
    }

    @Test
    fun answersParsing() {
        val a = Answer.parse("", "")
        val b = Answer.parse("3", "2")
        val c = Answer.parse("2", "1")

        assertEquals(a, Left("The kows must be integer".toError()))
        assertEquals(b, Right(Answer(Kows(3), Bulls(2))))
        assertEquals(c, Right(Answer(Kows(2), Bulls(1))))
    }

    @Test
    fun answersValidation() {
        val a = Answer(Kows(3), Bulls(2))
        val b = Answer(Kows(10), Bulls(20))
        val c = Answer(Kows(2), Bulls(2))

        assertEquals(a.validate(), Left("Sum of kows and bulls must be between 0 and 4".toError()))
        assertEquals(b.validate(), Left("Kows must be between 0 and 4".toError()))
        assertEquals(c.validate(), Right(Answer(Kows(2), Bulls(2))))
    }
}
