package site.aiion.api.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.aiion.api.schedule.domain.ScheduleEntity;
import site.aiion.api.schedule.domain.ScheduleDTO;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, ScheduleDTO> {
    
}

