package avada.media.usainua_api.service;

import avada.media.usainua_api.model.BankingCard;

import java.util.List;

public interface BankingCardService {

    void saveBankingCard(BankingCard bankingCard);

    void deleteBankingCardById(Long id);

    List<BankingCard> getAllBankingCardsByUserId(Long id);

    BankingCard getDefaultBankingCard();

    BankingCard getBankingCardById(Long id);

}
