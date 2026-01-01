package traya.hairtest.form_filling.service.userDemographicDetailsSaveOrUpdate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traya.hairtest.form_filling.dto.request.UserDemographicRequestDto;
import traya.hairtest.form_filling.dto.response.UserDemographicResponseDto;
import traya.hairtest.form_filling.entity.UserEntity;
import traya.hairtest.form_filling.repository.UserRepository;
import traya.hairtest.form_filling.service.userDemographicDetailsSaveOrUpdate.UserDemographicDetails;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserDemographicDetailsImpl implements UserDemographicDetails {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDemographicResponseDto saveOrUpdateuserDemographicDetails(
            UserDemographicRequestDto userDemographicRequest
    ) {
        Optional<UserEntity> user = userRepository.findById(userDemographicRequest.getPhone());

        if(user.isPresent()){
            setUserDemographicDetails(user.get(),userDemographicRequest);
            return UserDemographicResponseDto.builder()
                    .status("UPDATED")
                    .build();
        }

        UserEntity newUser = new UserEntity();
        newUser.setPhone(userDemographicRequest.getPhone());
        newUser.setCreatedAt(LocalDateTime.now());
        setUserDemographicDetails(newUser,userDemographicRequest);
        return UserDemographicResponseDto.builder()
                .status("SAVED")
                .build();
    }

    private void setUserDemographicDetails(
            UserEntity user,
            UserDemographicRequestDto userDemographicRequest
    ){
        user.setName(userDemographicRequest.getName());
        user.setAge(userDemographicRequest.getAge());
        user.setGender(userDemographicRequest.getGender());
        user.setLocation(userDemographicRequest.getLocation());

        //reset form status for user
        user.setCurrentQuestionIndex(0);
        user.setSubmitted(false);
        user.setLastUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}
