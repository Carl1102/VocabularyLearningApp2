# Vocabulary Learning App

## Overview
Vocabulary Learning App is an Android application developed with Jetpack Compose.
It helps users learn English vocabulary through flashcards, quizzes, settings customization, progress tracking, and online definition lookup.

This app was designed as an educational mobile application project. It demonstrates user interface design, screen navigation, local data persistence, internet connectivity, and unit testing.

---

## Features

### 1. Landing Page
- Displays the app title and introduction
- Shows the daily learning goal
- Lists the main app features
- Includes an ethical design note
- Provides navigation to all main screens

### 2. Learning Activity Screen
- Displays vocabulary flashcards
- Provides multiple-choice quiz questions
- Shows whether the selected answer is correct or incorrect
- Allows users to move to the next word
- Loads an online definition using a public dictionary API

### 3. Settings Screen
- Allows users to:
  - turn sound on or off
  - turn dark mode on or off
  - select difficulty level
  - adjust the daily learning goal
- Saves user preferences locally using SQLite

### 4. Statistics Screen
- Displays learning statistics, including:
  - total words studied
  - quiz accuracy
  - best score
  - completed sessions
  - current streak
- Allows users to update and reset statistics
- Saves statistics locally using SQLite

---

## Technologies Used
- Kotlin
- Android Studio
- Jetpack Compose
- Navigation Compose
- SQLiteOpenHelper
- HTTP networking with `HttpURLConnection`
- JUnit for unit testing

---

## App Structure

com.wangbohong.vocabularylearningapp2  
├── data  
│   ├── local  
│   │   └── AppDatabaseHelper.kt  
│   └── remote  
│       └── VocabularyRepository.kt  
├── logic  
│   ├── QuizUtils.kt  
│   ├── SettingsUtils.kt  
│   └── StatisticsUtils.kt  
├── ui  
│   ├── navigation  
│   │   └── AppNavGraph.kt  
│   ├── screens  
│   │   ├── activity  
│   │   │   └── ActivityScreen.kt  
│   │   ├── landing  
│   │   │   └── LandingScreen.kt  
│   │   ├── settings  
│   │   │   └── SettingsScreen.kt  
│   │   └── statistics  
│   │       └── StatisticsScreen.kt  
│   └── theme  
└── MainActivity.kt

---

## Internet Connectivity
The app connects to a public dictionary API to fetch online word definitions.
This feature is implemented in the Learning Activity screen.

---

## Local Data Storage
SQLite is used to store:
- user settings
- user statistics

This allows the app to keep data after the user leaves a screen or restarts the app.

---

## Ethical Design Considerations
This app includes several ethical design principles:
- Privacy: it only stores necessary learning data locally
- Transparency: it clearly shows what data is used in the app
- User control: settings can be changed by the user at any time
- Accessibility: the interface is simple, readable, and easy to navigate

---

## Testing
The app includes local unit tests for core logic:
- QuizUtilsTest
- StatisticsUtilsTest
- SettingsUtilsTest

These tests verify:
- answer checking logic
- statistics update logic
- daily goal boundary logic

---

## How to Run the App
1. Open the project in Android Studio
2. Sync Gradle files
3. Start an Android emulator or connect a device
4. Click Run
5. Use the Landing Page to navigate through the app

---

## Future Improvements
- Add more vocabulary sets
- Add user profiles
- Improve quiz variety
- Add pronunciation audio
- Improve UI styling and accessibility further

---

## Author
Wang Bohong

---

## GitHub Repository
https://github.com/Carl1102/VocabularyLearningApp2
