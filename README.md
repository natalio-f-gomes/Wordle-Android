# Wordle Android Game

A Wordle clone built with Kotlin and Jetpack Compose for Android.

## Overview

This is a functional implementation of the popular word-guessing game Wordle. Plyers haave 6 attempts to guess a random 5-letter word, receiving color-coded feedback after each guess.

## Features

- **Classic Wordle Gameplay**: Guess a 5-letter word in 6 tries
- **Color-Coded Feedback**:
  - 🟩 Green: Correct letter in the correct position
  - 🟨 Yellow: Correct letter in the wrong position
  - ⬜ Gray: Letter not in the word
- **Word Validation**: Only accepts valid words from the word list
- **On-Screen Keyboard**: Custom keyboard interface for letter input
- **Real-Time Feedback**: Immediate visual updates as you type

## Project Structure

```
src/main/java/com/example/wordle/
├── MainActivity.kt          # Entry point, loads word list
├── WordleScreen.kt         # Main screen composable
└── WordleLogic.kt          # Game logic and UI components
    ├── BoardSection()      # Displays the guess grid
    ├── KeyboardSection()   # Virtual keyboard
    └── evaluateWord()      # Word evaluation logic
```

## Technical Details

**Built With:**
- Kotlin
- Jetpack Compose
- Material3 Design

**Key Components:**
- `Letter` data class: Represents each letter tile with character, position, and color
- `BoardSection`: Renders the 6×5 game grid
- `KeyboardSection`: Handles input and game state management
- `evaluateWord()`: Determines letter colors based on the target word

## Word List

The game loads its word list from `assets/wordle.txt`. This file should contain valid 5-letter words, one per line.

## Game Rules

1. Enter a 5-letter word using the on-screen keyboard
2. Press ✓ to submit your guess
3. Letters are colored based on their accuracy:
   - Green = correct letter, correct position
   - Yellow = correct letter, wrong position
   - Gray = letter not in word
4. Invalid words are rejected (turns dark gray)
5. Win by guessing the word within 6 tries

## How to Run

1. Clone this repository
2. Build the Android project in Android Studio with the same name as this repository and choose this repository as the project location.
3. Ensure you have a `wordle.txt` file in `src/main/assets/` with 5-letter words
4. Build and run on an Android device or emulator (API 21+)

## Game State Management

The game uses Compose's state management:
- `board`: 2D array tracking all letter tiles
- `wordTyped`: Current word being typed
- `letterIndex`/`letterRow`: Track cursor position
- `tries`: Count of submitted guesses
- `message`: User feedback display

## Future Improvements

Potential enhancements could include:
- Hard mode (must use revealed letters)
- Statistics tracking
- Share results functionality
- Dark mode support
- Daily word challenge
- Keyboard key coloring based on used letters

## License

This is a student project for educational purposes.