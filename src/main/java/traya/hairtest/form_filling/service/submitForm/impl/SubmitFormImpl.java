package traya.hairtest.form_filling.service.submitForm.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traya.hairtest.form_filling.dto.request.SubmitFormRequestDto;
import traya.hairtest.form_filling.dto.response.SubmitFormResponseDto;
import traya.hairtest.form_filling.entity.UserEntity;
import traya.hairtest.form_filling.repository.UserRepository;
import traya.hairtest.form_filling.service.submitForm.SubmitForm;
import traya.hairtest.form_filling.utils.QuestionnareLoader;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SubmitFormImpl implements SubmitForm {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionnareLoader questionnareLoader;

    @Override
    public SubmitFormResponseDto submitForm(SubmitFormRequestDto request) {
        UserEntity user = userRepository.findById(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // No active form
        if (Boolean.TRUE.equals(user.getSubmitted())) {
            throw new RuntimeException("No active form to submit");
        }

        int totalQuestions =
                questionnareLoader
                        .get(user.getGender().name())
                        .getQuestions()
                        .size();
        if (user.getCurrentQuestionIndex() < totalQuestions) {
            throw new RuntimeException("Form is not yet complete");
        }

        user.setSubmitted(true);
        user.setLastUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return SubmitFormResponseDto.builder()
                .submitted(true)
                .build();
    }
}
