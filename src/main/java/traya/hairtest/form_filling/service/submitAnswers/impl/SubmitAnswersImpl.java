package traya.hairtest.form_filling.service.submitAnswers.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traya.hairtest.form_filling.dto.Category;
import traya.hairtest.form_filling.dto.Question;
import traya.hairtest.form_filling.dto.Questionnare;
import traya.hairtest.form_filling.dto.request.SubmitAnswerRequestDto;
import traya.hairtest.form_filling.dto.response.SubmitAnswerResponseDto;
import traya.hairtest.form_filling.entity.UserEntity;
import traya.hairtest.form_filling.repository.UserRepository;
import traya.hairtest.form_filling.service.submitAnswers.SubmitAnswers;
import traya.hairtest.form_filling.utils.QuestionnareLoader;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class SubmitAnswersImpl implements SubmitAnswers {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private final QuestionnareLoader questionnareLoader;

    @Override
    public SubmitAnswerResponseDto submitAnswer(SubmitAnswerRequestDto request) {

        UserEntity user = fetchUser(request.getPhone());
        Questionnare questionnaire = questionnareLoader.get(user.getGender().name());

        validateActiveForm(user);

        int totalQuestions =
                questionnaire.getCategories().stream()
                        .mapToInt(cat -> cat.getQuestions().size())
                        .sum();
        int expectedQuestion = getExpectedQuestion(user, totalQuestions);

        validateQuestionOrder(request.getQuestionNumber(), expectedQuestion) ;


        Question question = getQuestion(questionnaire.getCategories(), request.getQuestionNumber());

        validateAnswers(question, request.getAnswers());

        Map<Integer, List<String>> answersMap = loadAnswers(user);
        answersMap.put(request.getQuestionNumber(), request.getAnswers());

        persistAnswers(user, answersMap);

        int nextQuestion = request.getQuestionNumber() + 1;
        updateProgress(user, request.getQuestionNumber());

        return buildResponse(nextQuestion, totalQuestions);
    }

    private UserEntity fetchUser(String phone) {
        return userRepository.findById(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private void validateActiveForm(UserEntity user) {
        if (Boolean.TRUE.equals(user.getSubmitted())) {
            throw new RuntimeException("No active form");
        }
    }

    private int getExpectedQuestion(UserEntity user, int totalQuestions) {
        int current = user.getCurrentQuestionIndex();

        if (current == 0) {
            return 1;
        }
        if (current == totalQuestions) {
            return totalQuestions;
        }
        return current + 1;
    }

    private void validateQuestionOrder(Integer actual, int expected) {
        if (!actual.equals(expected)) {
            throw new RuntimeException("Invalid question order");
        }
    }

    private Question getQuestion(List<Category> categories, int questionNumber) {
        return categories.stream()
                .flatMap(cat -> cat.getQuestions().stream())
                .filter(q -> q.getNumber() == questionNumber)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Invalid question number"));
    }

    private void validateAnswers(Question question, List<String> answers) {

        switch (question.getType()) {
            case "SCQ" -> {
                if (answers.size() != 1) {
                    throw new RuntimeException("SCQ must have exactly one answer");
                }
            }
            case "MCQ" -> {
                if (answers.isEmpty()) {
                    throw new RuntimeException("MCQ must have at least one answer");
                }
            }
            default -> throw new RuntimeException("Unsupported question type");
        }

        Set<String> validOptions = new HashSet<>(question.getOptions());
        for (String answer : answers) {
            if (!validOptions.contains(answer)) {
                throw new RuntimeException("Invalid answer option: " + answer);
            }
        }
    }

    private Map<Integer, List<String>> loadAnswers(UserEntity user) {
        try {
            if (user.getAnswersJson() == null) {
                return new HashMap<>();
            }
            return objectMapper.readValue(
                    user.getAnswersJson(),
                    new TypeReference<>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse answers JSON", e);
        }
    }

    private void persistAnswers(UserEntity user, Map<Integer, List<String>> answersMap) {
        try {
            user.setAnswersJson(objectMapper.writeValueAsString(answersMap));
        } catch (Exception e) {
            throw new RuntimeException("Failed to save answers", e);
        }
    }

    private void updateProgress(UserEntity user, int nextQuestion) {
        user.setCurrentQuestionIndex(nextQuestion);
        user.setLastUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    private SubmitAnswerResponseDto buildResponse(int nextQuestion, int totalQuestions) {
        if (nextQuestion > totalQuestions) {
            return SubmitAnswerResponseDto.builder()
                    .completed(true)
                    .build();
        }

        return SubmitAnswerResponseDto.builder()
                .nextQuestionNumber(nextQuestion)
                .build();
    }
}
