package traya.hairtest.form_filling.service.userDemographicDetailsSaveOrUpdate;

import traya.hairtest.form_filling.dto.request.UserDemographicRequestDto;
import traya.hairtest.form_filling.dto.response.UserDemographicResponseDto;

public interface UserDemographicDetails {
    public UserDemographicResponseDto saveOrUpdateuserDemographicDetails(UserDemographicRequestDto userDemographicRequest);
}
