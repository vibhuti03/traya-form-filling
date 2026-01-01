package traya.hairtest.form_filling.service.submitAnswers.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traya.hairtest.form_filling.dto.Question;
import traya.hairtest.form_filling.dto.request.SubmitAnswerRequestDto;
import traya.hairtest.form_filling.dto.response.SubmitAnswerResponseDto;
import traya.hairtest.form_filling.entity.UserEntity;
import traya.hairtest.form_filling.repository.UserRepository;
import traya.hairtest.form_filling.service.submitAnswers.SubmitAnswers;
import traya.hairtest.form_filling.utils.QuestionnareLoader;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        UserEntity user = userRepository.findById(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (Boolean.TRUE.equals(user.getSubmitted())) {
            throw new RuntimeException("Form already submitted");
        }

        int expectedQuestion =
                user.getCurrentQuestionIndex() == 0 ? 1 : user.getCurrentQuestionIndex();

        if (!request.getQuestionNumber().equals(expectedQuestion)) {
            throw new RuntimeException("Invalid question order");
        }

        Question question =
                questionnareLoader
                        .get(user.getGender().name())
                        .getQuestions()
                        .stream()
                        .filter(q -> q.getNumber() == request.getQuestionNumber())
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException("Invalid question number"));

        if ("SCQ".equals(question.getType())
                && request.getAnswers().size() != 1) {
            throw new RuntimeException("SCQ must have exactly one answer");
        }

        if ("MCQ".equals(question.getType())
                && request.getAnswers().isEmpty()) {
            throw new RuntimeException("MCQ must have at least one answer");
        }

        Set<String> validOptions = new HashSet<>(question.getOptions());

        for (String answer : request.getAnswers()) {
            if (!validOptions.contains(answer)) {
                throw new RuntimeException(
                        "Invalid answer option: " + answer
                );
            }
        }


        // --- Load existing answers ---
        Map<Integer, Object> answersMap = new HashMap<>();

        try {
            if (user.getAnswersJson() != null) {
                answersMap = objectMapper.readValue(
                        user.getAnswersJson(),
                        new TypeReference<>() {}
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse answers JSON", e);
        }

        // --- Store answer ---
        answersMap.put(request.getQuestionNumber(), request.getAnswers());

        try {
            user.setAnswersJson(objectMapper.writeValueAsString(answersMap));
        } catch (Exception e) {
            throw new RuntimeException("Failed to save answer", e);
        }

        // --- Increment question ---
        int nextQuestion = request.getQuestionNumber() + 1;
        user.setCurrentQuestionIndex(nextQuestion);
        user.setLastUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        // --- Check completion ---
        int totalQuestions =
                questionnareLoader
                        .get(user.getGender().name())
                        .getQuestions()
                        .size();

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
