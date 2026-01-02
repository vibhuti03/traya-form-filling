package traya.hairtest.form_filling.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import traya.hairtest.form_filling.constants.Gender;

@Data
public class UserDemographicRequestDto {
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be 10 digits and start with 6,7,8,9"
    )
    private String phone;

    @NotBlank
    private String name;

    @Min(18)
    @Max(99)
    private Integer age;

    @NotNull
    private Gender gender;
}
