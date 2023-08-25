package ke.vincent.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ke.vincent.cards.models.ERole;

public interface RoleDAO extends JpaRepository<ERole, Integer> {
    
}
