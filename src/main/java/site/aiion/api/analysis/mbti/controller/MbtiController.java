package site.aiion.api.analysis.mbti.controller;

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
import site.aiion.api.analysis.mbti.domain.MbtiDTO;
import site.aiion.api.analysis.mbti.service.MbtiService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mbti")
public class MbtiController {

    private final MbtiService mbtiService;

    @PostMapping("/findById")
    public Messenger findById(@RequestBody MbtiDTO mbtiDTO) {
        return mbtiService.findById(mbtiDTO);
    }

    @GetMapping
    public Messenger findAll() {
        return mbtiService.findAll();
    }

    @PostMapping
    public Messenger save(@RequestBody MbtiDTO mbtiDTO) {
        return mbtiService.save(mbtiDTO);
    }

    @PostMapping("/saveAll")
    public Messenger saveAll(@RequestBody List<MbtiDTO> mbtiDTOList) {
        return mbtiService.saveAll(mbtiDTOList);
    }

    @PutMapping
    public Messenger update(@RequestBody MbtiDTO mbtiDTO) {
        return mbtiService.update(mbtiDTO);
    }

    @DeleteMapping
    public Messenger delete(@RequestBody MbtiDTO mbtiDTO) {
        return mbtiService.delete(mbtiDTO);
    }
}

