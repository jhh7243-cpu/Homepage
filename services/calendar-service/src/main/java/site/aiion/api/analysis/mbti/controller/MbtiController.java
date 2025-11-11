package site.aiion.api.analysis.mbti.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.analysis.mbti.service.MbtiService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mbti")
public class MbtiController {

    private final MbtiService mbtiService;

    @GetMapping("/{id}")
    public Messenger findById(@PathVariable Long id) {
        return mbtiService.findById(id);
    }

    @GetMapping
    public Messenger findAll() {
        return mbtiService.findAll();
    }
}

