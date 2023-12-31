/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package se.yankov.kows

object App {
    fun gameLoop() {
        var candidates: Collection<Candidate> = Candidate.all
        val computersNumber: Candidate = Candidate.all.random()

        while (true) {
            while (true) {
                print("Ask the computer for its number: ")
                val input: String = readlnOrNull() ?: ""

                val result: Either<Candidate> = Candidate.parse(input).flatMap(Candidate::validate)
                when (result) {
                    is Left -> println("Input error: ${result.value}; Try again!")
                    is Right -> {
                        val answer: Answer = computersNumber.compare(result.value)
                        println("${answer.bulls}, ${answer.kows}")
                        if (answer.isWin()) {
                            println()
                            println("You won!!!")
                            return
                        }

                        break
                    }
                }
            }

            val computersQuery: Candidate =
                candidates.randomOrNull() ?: run<App, Nothing> {
                    println("There are no possible numbers matching your answers! The computer is sad :(")
                    return
                }

            while (true) {
                println("The computer asks for your number: $computersQuery")

                print("How many bulls: ")
                val bullsInput: String = readlnOrNull() ?: ""
                print("How many kows: ")
                val kowsInput: String = readlnOrNull() ?: ""

                val result: Either<Answer> = Answer.parse(kowsInput = kowsInput, bullsInput = bullsInput).flatMap(Answer::validate)
                when (result) {
                    is Left -> println("Input error: ${result.value}; Try again!")
                    is Right -> {
                        val (answer) = result
                        if (answer.isWin()) {
                            println()
                            println("The computer won!!!")
                            return
                        }

                        candidates = candidates.filter { answer == computersQuery.compare(it) }

                        break
                    }
                }
            }
        }
    }
}

fun main() = App.gameLoop()
