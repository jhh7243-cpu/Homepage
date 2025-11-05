package site.aiion.api.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.aiion.api.player.domain.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    
}

