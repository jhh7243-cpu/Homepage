package site.aiion.api.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import site.aiion.api.calendar.domain.Calender;

@Repository
public interface CalenderRepository extends JpaRepository<Calender, Long> {
    
}

