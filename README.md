# QuizMaster

A modern Android quiz application built with Kotlin and Jetpack Compose.  
The app focuses on clean architecture, reactive UI state handling, smooth user interactions, and a polished user experience.

The goal of the project was not only to build a functional quiz flow, but also to structure it in a way that can scale as the application grows.

---

## Screenshots

### Quiz Screen
<img width="1080" height="2400" alt="Screenshot_20260723_190059" src="https://github.com/user-attachments/assets/1d68c189-093f-4a74-ae68-87d183123463" />
<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/6b140bdf-5551-45d3-8b08-fc1168406224" />

### Answer Feedback
<img width="1080" height="2400" alt="Screenshot_20260723_185825" src="https://github.com/user-attachments/assets/112f4652-a2cf-4620-8fe8-264676b93ca3" />
<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/1a08644b-c550-4888-8d53-0990b8cfc4c4" />

### Error Feedback
<img width="1080" height="2400" alt="Screenshot_20260723_185845" src="https://github.com/user-attachments/assets/9d333ad2-8eb0-4d08-b624-dd6b8e584538" />
<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/d8933ebb-d255-47f0-9e0e-501cf6fe45f0" />

### Results Screen
<img width="1080" height="2400" alt="Screenshot_20260723_185919" src="https://github.com/user-attachments/assets/0a4d1d62-7266-4f32-ba59-d04b25b7fc92" />
<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/59ed05b6-44a3-47fb-912f-dc9aee5b5435" />

---

# Features

## Quiz Flow

- Loads quiz questions from a local JSON source.
- Displays questions with multiple answer options.
- Supports:
  - Answer selection
  - Skip questions
  - Question progress tracking
  - Automatic navigation between questions
  - Final result calculation

## Answer Feedback

After answering:

- Shows whether the answer was correct or incorrect.
- Highlights the correct answer when needed.
- Provides a smooth transition before moving to the next question.
- Prevents accidental multiple submissions during feedback.

## Streak System

The app tracks user performance through streaks:

- Current streak
- Longest streak
- Visual streak indicator

## Results Summary

At the end of the quiz, users can view:

- Final percentage
- Correct answers
- Incorrect answers
- Skipped questions
- Best streak achieved

---

# Tech Stack

## Language

- Kotlin

## UI

- Jetpack Compose
- Material 3
- Custom typography and theming
- Compose animations

## Architecture

- MVVM
- Clean Architecture principles

Layers:

```
presentation
    |
domain
    |
data
```

## Dependency Injection

- Hilt

Used for:

- Repository injection
- ViewModel injection
- Dependency management

## Asynchronous / State Handling

- Kotlin Coroutines
- StateFlow
- SharedFlow

Used for:

- Reactive UI updates
- One-time navigation events
- Loading and error states

## Navigation

- Jetpack Navigation Compose

---

# Architecture Overview

The project follows a Clean Architecture inspired approach.

## Presentation Layer

Responsible for:

- Compose UI
- UI state rendering
- User interactions
- Navigation events

Example:

```
QuizScreen
    |
QuizViewModel
    |
QuizUiState
```

The UI observes state changes and does not directly interact with data sources.

---

## Domain Layer

Contains business logic independent of Android frameworks.

Includes:

- Models
- Repository contracts
- Use cases

Example:

```
SubmitAnswerUseCase
MoveToNextQuestionUseCase
GetQuizResultUseCase
```

This keeps business rules testable and separate from implementation details.

---

## Data Layer

Responsible for:

- Reading quiz data
- Parsing JSON
- Repository implementation

Flow:

```
questions.json

      ↓

QuestionJsonParser

      ↓

QuizRepositoryImpl

      ↓

Domain Models
```

---

# State Management

The UI uses a single source of truth through `StateFlow`.

Example state flow:

```
Repository
     |
Use Case
     |
ViewModel
     |
StateFlow
     |
Compose UI
```

UI states are handled explicitly:

```
Loading
Success
Error
```

This avoids hidden states and makes failures easier to handle.

---

# Design Decisions

## Why Clean Architecture?

The quiz logic is separated from UI implementation so features can be modified without affecting other layers.

For example:

Changing the quiz source from local JSON to an API would only require changes in the data layer.

---

## Why StateFlow?

StateFlow provides:

- Lifecycle-aware state collection
- A single observable UI state
- Predictable recompositions

The UI simply renders the current state.

---

## Why SharedFlow?

SharedFlow is used for one-time events such as:

- Navigation events
- Actions that should not replay after configuration changes

---

## Error Handling

The application handles failures during initialization.

Examples:

- Invalid JSON
- Parsing failures
- Missing quiz data

Errors are propagated through the UI state and displayed using a dedicated error screen.

---

# Performance Considerations

## Compose Optimization

- Uses stable UI state objects.
- Avoids unnecessary recompositions.
- Uses lifecycle-aware state collection.
- Keeps composables focused and reusable.

## User Experience

- Responsive layouts using Compose modifiers.
- Scrollable screens for different device sizes.
- Material components for accessibility consistency.
- Proper spacing and typography system.

---

# Running the Project

Requirements:

- Android Studio Ladybug or newer
- Android SDK 36
- Kotlin 2.x

Steps:

1. Clone the repository

```
git clone <repository-url>
```

2. Open the project in Android Studio

3. Sync Gradle

4. Run on an emulator or physical device

---

# Author

Anirudh Vinod
