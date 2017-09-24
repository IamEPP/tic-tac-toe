package br.com.epp.program

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class TicTacToeSpec {
    @Rule
    @JvmField
    var exception: ExpectedException = ExpectedException.none()

    lateinit var ticTacToe: TicTacToe

    @Before
    fun setup() {
        ticTacToe = TicTacToe()
    }

    @Test
    fun whenXOutsideBoardThenRuntimeException() {
        exception.expect(RuntimeException::class.java)
        ticTacToe.play('X', 4, 3)
    }

    @Test
    fun whenYOutsideBoardThenRuntimeException() {
        exception.expect(RuntimeException::class.java)
        ticTacToe.play('X', 3, 0)
    }

    @Test
    fun whenXPlacedInOcupiedSpaceThenRuntimeException() {
        ticTacToe.play('X', 3, 3)
        exception.expect(RuntimeException::class.java)
        ticTacToe.play('O', 3, 3)
    }


    @Test
    fun givenLastPlayerWasXWhenXPlayConsecutivelyThenRuntimeException() {
        ticTacToe.play('X', 1, 2)
        exception.expect(RuntimeException::class.java)
        ticTacToe.play('X', 1, 3)
    }

    @Test
    fun givenFirstTurnWhenNextPlayerThenX() {
        var nextPlayer = ticTacToe.nexPlayer()
        assertEquals('X', nextPlayer)
    }

    @Test
    fun givenTheLastPlayerWasXWhenNextPlayerThenO() {
        ticTacToe.play('X', 1, 1)
        var nextPlayer = ticTacToe.nexPlayer()
        assertEquals('O', nextPlayer)
    }

    @Test
    fun givenTheLastPlayerWasOWhenNextPlayerThenO() {
        ticTacToe.play('X', 1, 1)
        ticTacToe.play('O', 1, 2)
        var nextPlayer = ticTacToe.nexPlayer()
        assertEquals('X', nextPlayer)
    }

    @Test
    fun whenPlayThenNoWinner() {
        var actual = ticTacToe.play('X', 1, 2)
        assertEquals("No winner", actual)
    }

    @Test
    fun whenPlayAndWholeHorizontalLineThenWinner() {
        ticTacToe.play('X', 1, 1)
        ticTacToe.play('O', 1, 2)
        ticTacToe.play('X', 2, 1)
        ticTacToe.play('O', 2, 2)
        val actual = ticTacToe.play('X', 3, 1)
        assertEquals("X is the winner", actual)
    }

    @Test
    fun whenPlayAndWholeVerticalLineThenWinner() {
        ticTacToe.play('X', 2, 1)
        ticTacToe.play('O', 1, 1)
        ticTacToe.play('X', 3, 1)
        ticTacToe.play('O', 1, 2)
        ticTacToe.play('X', 2, 2)
        val actual = ticTacToe.play('O', 1, 3)
        assertEquals("O is the winner", actual)
    }

    @Test
    fun whenPlayAndBottomTopDiagonalLineThenWinner() {
        ticTacToe.play('X', 1, 3)
        ticTacToe.play('O', 1, 1)
        ticTacToe.play('X', 2, 2)
        ticTacToe.play('O', 1, 2)
        val actual = ticTacToe.play('X', 3, 1)
        assertEquals("X is the winner", actual)
    }

    @Test
    fun whenAllBoxesAreFilledThenDraw() {
        ticTacToe.play('X', 1, 1)
        ticTacToe.play('O', 1, 2)
        ticTacToe.play('X', 1, 3)
        ticTacToe.play('O', 2, 1)
        ticTacToe.play('X', 2, 3)
        ticTacToe.play('O', 2, 2)
        ticTacToe.play('X', 3, 1)
        ticTacToe.play('O', 3, 3)
        val actual = ticTacToe.play('X', 3, 2)
        assertEquals("The result is draw", actual)
    }
}
