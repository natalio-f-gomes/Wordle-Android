package com.example.wordle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wordle.ui.theme.WordleTheme
import com.example.wordlestudent.WordleScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val wordList = this.assets.open("wordle.txt").bufferedReader().useLines { it.toList() }
        Log.d("WordList", wordList[0])
        setContent {
            WordleTheme {
                //  WordleScreen(WordleLogic(wordList))
                WordleScreen(wordList)
            }
        }
    }
}