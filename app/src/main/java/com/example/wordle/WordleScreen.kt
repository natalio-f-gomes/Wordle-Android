package com.example.wordlestudent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordle.BoardSection
import com.example.wordle.KeyboardSection
import com.example.wordle.Letter

// keyboardSection(randomWord selected) - call the board section and pass the letter input by the user
    // handle if the letter is correct
        //edit the letter row, col and color
        // boardSection(letter input by the user)


@Composable
fun WordleScreen(wordList: List<String>? = null) {
    val randomWord = remember { wordList?.random() }
    println(randomWord)

    var board by remember {
        mutableStateOf(
            Array(6) { row ->
                Array(5) { col ->
                    Letter("", row, col)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        BoardSection(board)
        Spacer(modifier = Modifier.height(16.dp))

        KeyboardSection(
            randomWord = randomWord,
            wordList = wordList,
            board = board,
            onBoardUpdate = { newBoard -> board = newBoard }
        )
    }
}


@Composable
@Preview
fun WordleScreenPreview(){
    WordleScreen()
}