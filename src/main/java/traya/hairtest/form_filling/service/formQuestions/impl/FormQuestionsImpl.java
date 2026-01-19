package traya.hairtest.form_filling.service.formQuestions.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traya.hairtest.form_filling.dto.Category;
import traya.hairtest.form_filling.dto.Question;
import traya.hairtest.form_filling.dto.Questionnare;
import traya.hairtest.form_filling.dto.request.FormQuestionRequestDto;
import traya.hairtest.form_filling.dto.response.FormQuestionResponseDto;
import traya.hairtest.form_filling.service.formQuestions.FormQuestions;
import traya.hairtest.form_filling.utils.QuestionnareLoader;

import java.util.List;

@Service
@AllArgsConstructor
public class FormQuestionsImpl implements FormQuestions {

    @Autowired
    private final QuestionnareLoader questionnareLoader;

    @Override
    public FormQuestionResponseDto getNextFormQuestion(FormQuestionRequestDto request) {

        Questionnare questionnaire =
                questionnareLoader.get(request.getGender());

        if (questionnaire == null) {
            throw new RuntimeException("Invalid gender requested");
        }

        int totalQuestions =
                questionnaire.getCategories().stream()
                        .mapToInt(cat -> cat.getQuestions().size())
                        .sum();

        for (Category category : questionnaire.getCategories()) {

            List<Question> questions = category.getQuestions();

            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);

                if (q.getNumber() == request.getNextQuestionNumber()) {

                    boolean isLastInCategory = (i == questions.size() - 1);
                    boolean isLastInForm = (q.getNumber() == totalQuestions);

                    return FormQuestionResponseDto.builder()
                            .questionNumber(q.getNumber())
                            .category(category.getName())
                            .type(q.getType())
                            .question(q.getText())
                            .options(q.getOptions())
                            .isLastQuestionInCategory(isLastInCategory)
                            .isLastQuestion(isLastInForm)
                            .build();
                }
            }
        }

        return FormQuestionResponseDto.builder()
                .completed(true)
                .build();
    }

}

