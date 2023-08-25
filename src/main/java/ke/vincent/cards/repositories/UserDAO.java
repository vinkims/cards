package ke.vincent.cards.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ke.vincent.cards.models.EUser;

public interface UserDAO extends JpaRepository<EUser, Integer>, JpaSpecificationExecutor<EUser> {

    Boolean existsByEmail(String email);
    
    Optional<EUser> findByEmail(String email);
}
