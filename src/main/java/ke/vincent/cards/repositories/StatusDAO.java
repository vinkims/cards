package ke.vincent.cards.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ke.vincent.cards.models.EStatus;

public interface StatusDAO extends JpaRepository<EStatus, Integer> {

    Optional<EStatus> findByName(String name);
}
