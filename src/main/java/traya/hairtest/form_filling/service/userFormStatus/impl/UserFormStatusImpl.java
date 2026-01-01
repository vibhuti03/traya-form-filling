package traya.hairtest.form_filling.service.userFormStatus.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traya.hairtest.form_filling.dto.response.UserFormStatusResponseDto;
import traya.hairtest.form_filling.entity.UserEntity;
import traya.hairtest.form_filling.repository.UserRepository;
import traya.hairtest.form_filling.service.userFormStatus.UserFormStatus;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserFormStatusImpl implements UserFormStatus {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserFormStatusResponseDto getUserFormStatus(String phone) {

        Optional<UserEntity> userFormData = userRepository.findById(phone);

        if(userFormData.isEmpty())
            return UserFormStatusResponseDto.builder()
                    .userExists(false)
                    .build();
        return null;
    }
}
