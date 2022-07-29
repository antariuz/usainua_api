package avada.media.usainua_api.service.impl;

import avada.media.usainua_api.model.user.PersonalData;
import avada.media.usainua_api.repository.PersonalDataRepo;
import avada.media.usainua_api.service.PersonalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PersonalDataServiceImpl implements PersonalDataService {

    private final PersonalDataRepo personalDataRepo;

    @Override
    public void savePersonalData(PersonalData personalData) {
        personalDataRepo.save(personalData);
    }

    @Override
    public PersonalData getPersonalDataById(Long id) {
        return personalDataRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found Personal Data with id: " + id));
    }

}
