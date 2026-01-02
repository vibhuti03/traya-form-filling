# Traya – Form Filling Backend

A Spring Boot backend service powering Traya’s hair health assessment flow.  
It handles user identification, form progress tracking, questionnaire delivery, answer submission, and final form submission.

## Demo Video

## ✨ Key Responsibilities

- Identify users via phone number
- Resume form from last unanswered question
- Serve category-based questionnaire questions
- Validate and persist answers
- Prevent duplicate submissions
- Track form progress and completion status



## 🧱 Tech Stack

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Jackson (JSON handling)
- Lombok



## 📂 Project Structure

```
src/main/java/
├── controller/ # REST controllers
├── service/ # Business logic
│ ├── formQuestions/
│ ├── submitAnswers/
│ └── submitForm/
├── repository/ # JPA repositories
├── entity/ # Database entities
├── dto/ # Request / Response DTOs
├── enums/ # Enums (Gender, Category, etc.)
├── utils/ # Questionnaire loader, helpers
└── FormFillingApplication.java
```

## ▶️ Run Locally
### Prerequisites

- Java 17+
- MySQL
- Maven

### Start the app
```
mvn clean install
mvn spring-boot:run
```


Server runs on:

`http://localhost:8080/api/v1/`
-


## 🔌 API Endpoints

### User Form Status
`POST /api/v1/user-form-status`
Returns whether the user exists, submission status, and next question index.



### User Demographics
`POST /api/v1/user-form-demographics`
Saves/Updates basic user details (name, age, gender, location).



### Next Question
`POST /api/v1/next-question`
Returns the next question based on:
- Gender
- Category (O)
- Question number

Also indicates:
- `isLastQuestionInCategory`
- `isLastQuestion`



### Submit Answer
`POST /api/v1/submit-answer`
- Validates SCQ / MCQ answers
- Ensures correct question order
- Persists answers
- Returns next question index or completion status



### Submit Form
`POST /api/v1/submit-form`
Marks the form as submitted after all questions are answered.



## 🗄️ Database

### User Table (MySQL)

- `phone` (Primary Key)
- `name`
- `age`
- `gender`
- `location`
- `answers_json`
- `current_question_index`
- `submitted`
- `created_at`
- `last_updated_at`

Schema is auto-managed via JPA (`ddl-auto=update`).



##  Validation Highlights

- Phone number format validation
- Strict question order enforcement
- SCQ / MCQ answer validation
- Prevents re-submission of completed forms


## Possible Enhancements

- Redis caching for questionnaire reads
- Versioned questionnaires
- Analytics events (question answered, form completed)
- Admin endpoints for questionnaire management

