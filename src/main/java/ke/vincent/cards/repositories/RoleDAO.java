package ke.vincent.cards.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ke.vincent.cards.models.ERole;

public interface RoleDAO extends JpaRepository<ERole, Integer> {
    
    Optional<ERole> findByName(String name);
}
