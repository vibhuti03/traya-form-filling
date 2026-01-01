package traya.hairtest.form_filling.service.userFormStatus;

import traya.hairtest.form_filling.dto.response.UserFormStatusResponseDto;

public interface UserFormStatus {
    public UserFormStatusResponseDto getUserFormStatus(String phone);
}
