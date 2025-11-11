package site.aiion.api.analysis.bigfive.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.analysis.bigfive.service.BigfiveService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bigfive")
public class BigfiveController {

    private final BigfiveService bigfiveService;

    @GetMapping("/{id}")
    public Messenger findById(@PathVariable Long id) {
        return bigfiveService.findById(id);
    }

    @GetMapping
    public Messenger findAll() {
        return bigfiveService.findAll();
    }
}

