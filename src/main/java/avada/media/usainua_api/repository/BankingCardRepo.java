package avada.media.usainua_api.repository;

import avada.media.usainua_api.model.BankingCard;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface BankingCardRepo extends JpaRepository<BankingCard, Long> {

    BankingCard findBankingCardByMainEquals(boolean main);

    List<BankingCard> findByUserId(Long id);

}
