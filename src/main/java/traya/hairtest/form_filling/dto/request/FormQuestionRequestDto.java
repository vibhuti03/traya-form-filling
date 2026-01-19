package traya.hairtest.form_filling.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormQuestionRequestDto {

    @NotNull
    private Integer nextQuestionNumber;

    private String category;

    @NotBlank
    private String gender;
}

