# QuizMaster

A modern Android quiz application built using Kotlin and Jetpack Compose.

The project focuses on clean architecture, reactive UI state management, smooth animations, reliable error handling, and preserving user progress across app restarts.

The goal was not only to build a working quiz application, but also to design a scalable structure where data sources, business logic, and UI remain independent.

---

# Screenshots

## Quiz Screen

<img width="1080" height="2400" alt="Quiz Screen" src="https://github.com/user-attachments/assets/1d68c189-093f-4a74-ae68-87d183123463" />

<img width="1080" height="2400" alt="Quiz Progress" src="https://github.com/user-attachments/assets/6b140bdf-5551-45d3-8b08-fc1168406224" />


## Answer Feedback

<img width="1080" height="2400" alt="Correct Answer Feedback" src="https://github.com/user-attachments/assets/112f4652-a2cf-4620-8fe8-264676b93ca3" />

<img width="1080" height="2400" alt="Wrong Answer Feedback" src="https://github.com/user-attachments/assets/1a08644b-c550-4888-8d53-0990b8cfc4c4" />


## Error Handling

<img width="1080" height="2400" alt="Error Screen" src="https://github.com/user-attachments/assets/9d333ad2-8eb0-4d08-b624-dd6b8e584538" />

<img width="1080" height="2400" alt="Network Error" src="https://github.com/user-attachments/assets/d8933ebb-d255-47f0-9e0e-501cf6fe45f0" />


## Results Screen

<img width="1080" height="2400" alt="Results Screen" src="https://github.com/user-attachments/assets/0a4d1d62-7266-4f32-ba59-d04b25b7fc92" />

<img width="1080" height="2400" alt="Result Summary" src="https://github.com/user-attachments/assets/59ed05b6-44a3-47fb-912f-dc9aee5b5435" />

---

# Features

## Quiz Flow

The application supports a complete quiz experience:

- Fetches quiz questions from a remote API.
- Converts API responses into domain models.
- Displays multiple-choice questions.
- Supports:
    - Answer selection
    - Skipping questions
    - Question progress tracking
    - Answer feedback
    - Automatic question navigation
    - Final score calculation

---

# API Integration

Quiz data is fetched using Retrofit.

The flow is:

```
Remote API

      ↓

Retrofit Service

      ↓

Repository

      ↓

Domain Models

      ↓

UI State
```

The application no longer depends on static JSON files.

This allows the data source to be replaced or modified without affecting the presentation layer.

---

# Error Handling

The application handles different failure scenarios during quiz initialization.

Handled cases:

## No Internet Connection

Before making the API request, connectivity is checked.

If there is no active network connection:

```
ConnectivityChecker

        ↓

NoInternetException

        ↓

QuizUiState.Error

        ↓

No Internet Screen
```

## Server/API Errors

HTTP failures from Retrofit are mapped into user-friendly error states.

Examples:

- Server unavailable
- Invalid response
- Request failure

## Unknown Errors

Unexpected failures are safely caught and displayed without crashing the application.

---

# Quiz Resume System

The application supports resuming an unfinished quiz after the app is killed or recreated.

Example:

1. User starts quiz.
2. API returns questions.
3. Quiz session is stored locally.
4. User answers questions.
5. App is closed.
6. User returns.
7. Existing progress is restored.

The application restores:

- Questions
- Current question index
- Correct answers
- Wrong answers
- Skipped answers
- Current streak
- Longest streak
- Question states

---

# Local Persistence Decision

## Why DataStore instead of Room?

Initially, Room was considered for storing quiz progress.

However, for this particular use case, DataStore was chosen.

The reason is that the application does not require querying, filtering, or manipulating individual questions.

The stored data represents one single object:

```
QuizSession

{
    questions,
    currentQuestionIndex,
    score,
    streak,
    questionStatus
}
```

This is essentially application state rather than relational data.

---

## Why Room was not used

Room would introduce:

- Database entities
- DAO interfaces
- Table relationships
- Type converters for lists
- Additional mapping between database models and domain models

Example:

```
Question Entity

QuestionOption Entity

Quiz Session Entity

Question Status Entity
```

For a production application with:

- Multiple quizzes
- Search
- History
- Analytics
- User profiles

Room would be the correct choice.

However, for this application:

- There is only one active quiz session.
- Questions are always refreshed from the API when starting a new quiz.
- We only need to restore the current state.
- No database queries are required.

The additional complexity of Room would outweigh its benefits.

---

## Why DataStore fits this scenario

DataStore provides:

- Simple key-value/object persistence.
- Coroutine support.
- Type-safe serialization.
- Lifecycle-safe reads and writes.

The stored object represents the current quiz state:

```
QuizSession

        ↓

DataStore

        ↓

Restored Quiz
```

This keeps the implementation simpler while satisfying the requirement of preserving progress.

---

# Resume Logic

When entering the quiz:

```
Open Quiz

      ↓

Check DataStore

      ↓

Existing started session?

        YES
         |
         ↓
     Restore session


        NO
         |
         ↓
     Fetch new quiz from API
```

A new API call is only performed when there is no active quiz session.

This prevents the user from losing progress if the API returns a different question set.

---

# Answer Feedback System

After answering:

- The selected answer is saved.
- Correct/wrong status is updated.
- Feedback animation is displayed.
- The user is automatically moved to the next question.

The feedback component handles:

- Correct answer indication
- Wrong answer indication
- Showing the correct option
- Final question completion state

---

# Streak System

The application tracks user performance through:

- Current streak
- Longest streak

The streak indicator includes:

- Animated appearance
- Pulse animation
- Dynamic updates

---

# Architecture

The project follows Clean Architecture principles.

Layers:

```
presentation

      |

domain

      |

data
```

---

# Presentation Layer

Responsible for:

- Jetpack Compose UI
- Rendering UI states
- Handling user actions
- Navigation events

Example:

```
QuizScreen

      |

QuizViewModel

      |

QuizUiState
```

The UI never directly communicates with repositories.

---

# Domain Layer

Contains application business logic.

Includes:

- Models
- Repository interfaces
- Use cases

Examples:

```
InitializeQuizUseCase

SubmitAnswerUseCase

MoveToNextQuestionUseCase

GetQuizResultUseCase
```

The domain layer remains independent from Android frameworks.

---

# Data Layer

Responsible for external data sources.

Includes:

- Retrofit API
- DataStore persistence
- Repository implementation
- Data mapping

Flow:

```
API Response

      ↓

DTO Mapper

      ↓

Domain Model

      ↓

Repository

      ↓

ViewModel
```

---

# Tech Stack

## Language

- Kotlin


## UI

- Jetpack Compose
- Material 3
- Custom animations
- Custom theming


## Architecture

- MVVM
- Clean Architecture


## Dependency Injection

- Hilt

Used for:

- Repository injection
- API service injection
- ViewModel injection


## Networking

- Retrofit
- Kotlin Coroutines


## Local Storage

- DataStore
- Kotlin Serialization


## State Management

- StateFlow
- SharedFlow

Used for:

StateFlow:
- Loading state
- Success state
- Error state
- Quiz updates

SharedFlow:
- Navigation events
- One-time UI events


## Navigation

- Jetpack Navigation Compose

---

# UI State Management

The application follows a single source of truth approach.

Flow:

```
Repository

      ↓

Use Case

      ↓

ViewModel

      ↓

StateFlow

      ↓

Compose UI
```

Possible UI states:

```
Loading

Success

Error
```

This makes state changes predictable and avoids hidden UI behaviour.

---

# Performance Considerations

## Compose Optimization

- Uses StateFlow with lifecycle-aware collection.
- Keeps composables reusable.
- Avoids unnecessary recompositions.
- Uses stable UI state models.

## User Experience

- Smooth animations.
- Responsive layouts.
- Material 3 components.
- Proper loading and error states.
- Progress preservation.

---

# Running The Project

Requirements:

- Android Studio Ladybug or newer
- Android SDK 36
- Kotlin 2.x


Steps:

1. Clone repository

```
git clone <repository-url>
```

2. Open project in Android Studio.

3. Sync Gradle.

4. Run on emulator or physical device.

---

# Author

Anirudh Vinod
