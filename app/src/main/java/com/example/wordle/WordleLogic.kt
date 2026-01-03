package com.example.wordle

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class Letter(
    val character: String = "",
    val row: Int = 0,
    val col: Int = 0,
    val color: Color = Color.LightGray
)

@Composable
fun BoardSection(board: Array<Array<Letter>>){
    //display the board, I  change it in a way that I can work with. So I understand my own code

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for(row in board) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                for (letter in row) {
                    Text(
                        text = letter.character,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(
                                letter.color,
                                RoundedCornerShape(4.dp)
                            )
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                            .wrapContentHeight(Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                }

            }
        }
    }
}

fun evaluateWord(randomWord: String?, wordTyped: String?): List<Color> {
    if (randomWord == null || wordTyped == null || wordTyped.length != 5) {
        return List(5) { Color.LightGray }
    }
    return wordTyped.mapIndexed { index, char ->
        when {
            randomWord[index] == char -> Color.Green
            randomWord.contains(char) -> Color.Yellow
            else -> Color.Gray
        }
    }
}
@Composable
fun KeyboardSection(
    randomWord: String?,
    wordList: List<String>?,
    board: Array<Array<Letter>>,
    onBoardUpdate: (Array<Array<Letter>>) -> Unit
) {
    var message by remember { mutableStateOf("") }
    var wordTyped by remember { mutableStateOf("") }
    var letterIndex by remember { mutableStateOf(0) }
    var letterRow by remember { mutableStateOf(0) }
    var tries by remember { mutableStateOf(0) }
    // Message area

    Text(
        text = message,
        modifier = Modifier.fillMaxWidth().background(Color(0xFFEFEFEF)).padding(8.dp),
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        color = Color.DarkGray
    )
    Spacer(modifier = Modifier.height(16.dp))

    val keyboard = listOf(
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf("✓", "Z", "X", "C", "V", "B", "N", "M", "⌫")
    )


    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        keyboard.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                row.forEach { key ->
                    Button(
                        onClick = {
                            when (key) {
                                "⌫" -> {
                                    if (wordTyped.isNotEmpty()) {
                                        wordTyped = wordTyped.dropLast(1)
                                        letterIndex--
                                        if (letterIndex < 0) letterIndex = 0

                                        // Remove letter from board
                                        val newBoard = board.copyOf()
                                        newBoard[letterRow][letterIndex] = Letter("", letterRow, letterIndex)
                                        onBoardUpdate(newBoard)
                                    }
                                }

                                "✓" -> {
                                    if (wordTyped.length == 5) {
                                        tries ++
                                        // Check if word is in list
                                        if (wordList?.contains(wordTyped.lowercase()) == false) {
                                            val newBoard = board.copyOf()
                                            for (i in 0 until 5) {
                                                newBoard[letterRow][i] = Letter(
                                                    wordTyped[i].toString(),
                                                    letterRow,
                                                    i,
                                                    Color.DarkGray
                                                )
                                            }
                                            onBoardUpdate(newBoard)

                                            message = "Word is not in the list"
                                            letterIndex = 0
                                            letterRow++
                                            wordTyped = ""

                                        } else {
                                            val colorResponse = evaluateWord(randomWord, wordTyped.lowercase())
                                            val newBoard = board.copyOf()
                                            for (i in 0 until 5) {
                                                newBoard[letterRow][i] = Letter(
                                                    wordTyped[i].toString(),
                                                    letterRow,
                                                    i,
                                                    colorResponse[i]
                                                )
                                            }
                                            onBoardUpdate(newBoard)
                                            // Move on to the next row
                                            letterIndex = 0
                                            letterRow++
                                            wordTyped = ""
                                            message = if (colorResponse.all { it == Color.Green }) {
                                                "You won in $tries tries!"
                                            } else if (letterRow >= 6) {
                                                "Game Over! Word was: $randomWord"
                                            } else {
                                                "It's a word"
                                            }
                                        }
                                    } else {
                                        message = "Enter a complete word"
                                    }
                                }
                                else -> {

                                    if (letterIndex < 5 && letterRow < 6) {
                                        val newBoard = board.copyOf()
                                        newBoard[letterRow][letterIndex] = Letter(
                                            key,
                                            letterRow,
                                            letterIndex,
                                            Color.LightGray
                                        )
                                        onBoardUpdate(newBoard)

                                        wordTyped += key
                                        letterIndex++
                                    } else if (letterIndex >= 5) {
                                        message = "Press ✓ to submit"
                                    }
                                }
                            }
                            Log.d("KeyPressed", key)
                            Log.d("WordTyped", wordTyped)
                        },
                        modifier = Modifier.weight(1f).aspectRatio(0.8f),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDDDDDD))
                    ) {
                        Text(key, color = Color.Black)
                    }
                }
            }
        }
    }

}

fun Array<Array<Letter>>.copyOf(): Array<Array<Letter>> {
    return Array(this.size) { row ->
        this[row].copyOf()
    }
}
