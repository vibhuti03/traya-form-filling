package traya.hairtest.form_filling.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
public class SubmitAnswerRequestDto {
    @NotBlank
    private String phone;

    @NotNull
    private Integer questionNumber;

    @NotNull
    private List<String> answers;
}
