package avada.media.usainua_api.service;

import avada.media.usainua_api.model.user.PersonalData;

public interface PersonalDataService {

    void savePersonalData(PersonalData personalData);

    PersonalData getPersonalDataById(Long id);

}
