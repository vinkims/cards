package ke.vincent.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ke.vincent.cards.models.ECard;

public interface CardDAO extends JpaRepository<ECard, Integer>, JpaSpecificationExecutor<ECard> {
    
}
