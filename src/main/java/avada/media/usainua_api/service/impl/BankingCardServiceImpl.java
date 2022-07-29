package avada.media.usainua_api.service.impl;

import avada.media.usainua_api.model.BankingCard;
import avada.media.usainua_api.repository.BankingCardRepo;
import avada.media.usainua_api.service.BankingCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BankingCardServiceImpl implements BankingCardService {

    private final BankingCardRepo bankingCardRepo;

    @Override
    public void saveBankingCard(BankingCard bankingCard) {
        bankingCardRepo.save(bankingCard);
    }

    @Override
    public void deleteBankingCardById(Long id) {
        bankingCardRepo.deleteById(id);
    }

    @Override
    public List<BankingCard> getAllBankingCardsByUserId(Long id) {
        return bankingCardRepo.findByUserId(id);
    }

    @Override
    public BankingCard getDefaultBankingCard() {
        return bankingCardRepo.findBankingCardByMainEquals(true);
    }

    @Override
    public BankingCard getBankingCardById(Long id) {
        return bankingCardRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found Banking Card with id: " + id));
    }

}
