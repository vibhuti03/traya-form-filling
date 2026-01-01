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

        Optional<UserEntity> userEntity = userRepository.findById(phone);

        if(userEntity.isEmpty())
            return UserFormStatusResponseDto.builder()
                    .userExists(false)
                    .build();

        UserEntity userFormData = userEntity.get();
        return UserFormStatusResponseDto.builder()
                .userExists(true)
                .name(userFormData.getName())
                .age(userFormData.getAge())
                .gender(userFormData.getGender())
                .location(userFormData.getLocation())
                .formSubmitted(userFormData.getSubmitted())
                .currentQuestionIndex(userFormData.getCurrentQuestionIndex())
                .build();

    }
}
