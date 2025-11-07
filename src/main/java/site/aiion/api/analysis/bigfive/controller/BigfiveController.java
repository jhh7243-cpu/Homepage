package site.aiion.api.analysis.bigfive.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.analysis.bigfive.domain.BigfiveDTO;
import site.aiion.api.analysis.bigfive.service.BigfiveService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bigfive")
public class BigfiveController {

    private final BigfiveService bigfiveService;

    @PostMapping("/findById")
    public Messenger findById(@RequestBody BigfiveDTO bigfiveDTO) {
        return bigfiveService.findById(bigfiveDTO);
    }

    @GetMapping
    public Messenger findAll() {
        return bigfiveService.findAll();
    }

    @PostMapping
    public Messenger save(@RequestBody BigfiveDTO bigfiveDTO) {
        return bigfiveService.save(bigfiveDTO);
    }

    @PostMapping("/saveAll")
    public Messenger saveAll(@RequestBody List<BigfiveDTO> bigfiveDTOList) {
        return bigfiveService.saveAll(bigfiveDTOList);
    }

    @PutMapping
    public Messenger update(@RequestBody BigfiveDTO bigfiveDTO) {
        return bigfiveService.update(bigfiveDTO);
    }

    @DeleteMapping
    public Messenger delete(@RequestBody BigfiveDTO bigfiveDTO) {
        return bigfiveService.delete(bigfiveDTO);
    }
}

