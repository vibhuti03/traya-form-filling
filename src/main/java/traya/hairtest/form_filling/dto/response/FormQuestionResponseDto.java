package traya.hairtest.form_filling.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormQuestionResponseDto {
    private Integer questionNumber;
    private Boolean isLastQuestion;
    private Boolean isLastQuestionInCategory;
    private String category;
    private String type;
    private String question;
    private List<String> options;
    private Boolean completed;
}
