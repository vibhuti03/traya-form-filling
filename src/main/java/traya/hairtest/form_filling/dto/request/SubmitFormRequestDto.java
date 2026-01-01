package traya.hairtest.form_filling.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SubmitFormRequestDto {
    @NotBlank
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be 10 digits and start with 6,7,8,9"
    )
    private String phone;
}
