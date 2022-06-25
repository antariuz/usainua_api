package avada.media.usainua_admin.repository;

import avada.media.usainua_admin.model.bankingCard.BankingCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankingCardRepository extends JpaRepository<BankingCard, Long> {
}
