package site.aiion.api.analysis.emotion.controller;

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
import site.aiion.api.analysis.emotion.domain.EmotionDTO;
import site.aiion.api.analysis.emotion.service.EmotionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emotion")
public class EmotionController {

    private final EmotionService emotionService;

    @PostMapping("/findById")
    public Messenger findById(@RequestBody EmotionDTO emotionDTO) {
        return emotionService.findById(emotionDTO);
    }

    @GetMapping
    public Messenger findAll() {
        return emotionService.findAll();
    }

    @PostMapping
    public Messenger save(@RequestBody EmotionDTO emotionDTO) {
        return emotionService.save(emotionDTO);
    }

    @PostMapping("/saveAll")
    public Messenger saveAll(@RequestBody List<EmotionDTO> emotionDTOList) {
        return emotionService.saveAll(emotionDTOList);
    }

    @PutMapping
    public Messenger update(@RequestBody EmotionDTO emotionDTO) {
        return emotionService.update(emotionDTO);
    }

    @DeleteMapping
    public Messenger delete(@RequestBody EmotionDTO emotionDTO) {
        return emotionService.delete(emotionDTO);
    }
}

