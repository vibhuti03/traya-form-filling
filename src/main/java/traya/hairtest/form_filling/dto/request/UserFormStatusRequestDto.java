package traya.hairtest.form_filling.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserFormStatusRequestDto {
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be 10 digits and start with 6, 7, 8, or 9"
    )
    private String phone;
}
