package site.aiion.api.calendar.service;

import java.util.List;

import site.aiion.api.calendar.domain.CalenderDTO;
import site.aiion.api.common.domain.Messenger;

public interface CalenderService {
    public Messenger findById(CalenderDTO calenderDTO);
    public Messenger findAll();
    public Messenger save(CalenderDTO calenderDTO);
    public Messenger saveAll(List<CalenderDTO> calenderDTOList);
    public Messenger update(CalenderDTO calenderDTO);
    public Messenger delete(CalenderDTO calenderDTO);
}

