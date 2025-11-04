package site.aiion.api.team.controller;

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
import site.aiion.api.team.domain.TeamDTO;
import site.aiion.api.team.service.TeamService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/findById")
    public Messenger findById(@RequestBody TeamDTO teamDTO) {
        return teamService.findById(teamDTO);
    }

    @GetMapping
    public Messenger findAll() {
        return teamService.findAll();
    }

    @PostMapping
    public Messenger save(@RequestBody TeamDTO teamDTO) {
        return teamService.save(teamDTO);
    }

    @PostMapping("/saveAll")
    public Messenger saveAll(@RequestBody List<TeamDTO> teamDTOList) {
        return teamService.saveAll(teamDTOList);
    }

    @PutMapping
    public Messenger update(@RequestBody TeamDTO teamDTO) {
        return teamService.update(teamDTO);
    }

    @DeleteMapping
    public Messenger delete(@RequestBody TeamDTO teamDTO) {
        return teamService.delete(teamDTO);
    }

}
