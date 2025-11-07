package site.aiion.api.calendar.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.aiion.api.calendar.domain.CalenderDTO;
import site.aiion.api.calendar.service.CalenderService;
import site.aiion.api.common.domain.Messenger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calenders")
public class CalenderController {

    private final CalenderService calenderService;

    @PostMapping("/findById")
    public Messenger findById(@RequestBody CalenderDTO calenderDTO) {
        return calenderService.findById(calenderDTO);
    }

    @GetMapping
    public Messenger findAll() {
        return calenderService.findAll();
    }

    @PostMapping
    public Messenger save(@RequestBody CalenderDTO calenderDTO) {
        return calenderService.save(calenderDTO);
    }

    @PostMapping("/saveAll")
    public Messenger saveAll(@RequestBody List<CalenderDTO> calenderDTOList) {
        return calenderService.saveAll(calenderDTOList);
    }

    @PutMapping
    public Messenger update(@RequestBody CalenderDTO calenderDTO) {
        return calenderService.update(calenderDTO);
    }

    @DeleteMapping
    public Messenger delete(@RequestBody CalenderDTO calenderDTO) {
        return calenderService.delete(calenderDTO);
    }
}

