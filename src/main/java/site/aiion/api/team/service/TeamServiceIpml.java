package site.aiion.api.team.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.team.domain.TeamDTO;
import site.aiion.api.team.domain.Team;
import site.aiion.api.team.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamServiceIpml implements TeamService {

    private final TeamRepository teamRepository;

    private TeamDTO entityToDTO(Team entity) {
        return TeamDTO.builder()
                .id(entity.getId())
                .team_Uk(entity.getTeam_Uk())
                .region_name(entity.getRegion_name())
                .team_name(entity.getTeam_name())
                .e_team_name(entity.getE_team_name())
                .orig_yyyy(entity.getOrig_yyyy())
                .zip_code1(entity.getZip_code1())
                .zip_code2(entity.getZip_code2())
                .address(entity.getAddress())
                .ddd(entity.getDdd())
                .tel(entity.getTel())
                .fax(entity.getFax())
                .homepage(entity.getHomepage())
                .owner(entity.getOwner())
                .stadium_Uk(entity.getStadium_Uk())
                .build();
    }

    @Override
    public Messenger findById(TeamDTO teamDTO) {
        Optional<Team> entity = teamRepository.findById(teamDTO.getId());
        if (entity.isPresent()) {
            TeamDTO dto = entityToDTO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("팀을 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<Team> entities = teamRepository.findAll();
        List<TeamDTO> dtoList = entities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return Messenger.builder()
                .Code(200)
                .message("전체 조회 성공: " + dtoList.size() + "개")
                .data(dtoList)
                .build();
    }

    @Override
    @Transactional
    public Messenger save(TeamDTO teamDTO) {
        Team entity = Team.builder()
                .team_Uk(teamDTO.getTeam_Uk())
                .region_name(teamDTO.getRegion_name())
                .team_name(teamDTO.getTeam_name())
                .e_team_name(teamDTO.getE_team_name())
                .orig_yyyy(teamDTO.getOrig_yyyy())
                .zip_code1(teamDTO.getZip_code1())
                .zip_code2(teamDTO.getZip_code2())
                .address(teamDTO.getAddress())
                .ddd(teamDTO.getDdd())
                .tel(teamDTO.getTel())
                .fax(teamDTO.getFax())
                .homepage(teamDTO.getHomepage())
                .owner(teamDTO.getOwner())
                .stadium_Uk(teamDTO.getStadium_Uk())
                .build();
        
        Team saved = teamRepository.save(entity);
        TeamDTO dto = entityToDTO(saved);
        return Messenger.builder()
                .Code(200)
                    .message("저장 성공: " + saved.getTeam_Uk())
                .data(dto)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<TeamDTO> teamDTOList) {
        List<Team> entities = teamDTOList.stream()
                .map(dto -> Team.builder()
                        .team_Uk(dto.getTeam_Uk())
                        .region_name(dto.getRegion_name())
                        .team_name(dto.getTeam_name())
                        .e_team_name(dto.getE_team_name())
                        .orig_yyyy(dto.getOrig_yyyy())
                        .zip_code1(dto.getZip_code1())
                        .zip_code2(dto.getZip_code2())
                        .address(dto.getAddress())
                        .ddd(dto.getDdd())
                        .tel(dto.getTel())
                        .fax(dto.getFax())
                        .homepage(dto.getHomepage())
                        .owner(dto.getOwner())
                        .stadium_Uk(dto.getStadium_Uk())
                        .build())
                .collect(Collectors.toList());
        
        List<Team> saved = teamRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(TeamDTO teamDTO) {
        Optional<Team> optionalEntity = teamRepository.findById(teamDTO.getId());
        if (optionalEntity.isPresent()) {
            Team entity = optionalEntity.get();
            entity.setRegion_name(teamDTO.getRegion_name());
            entity.setTeam_name(teamDTO.getTeam_name());
            entity.setE_team_name(teamDTO.getE_team_name());
            entity.setOrig_yyyy(teamDTO.getOrig_yyyy());
            entity.setZip_code1(teamDTO.getZip_code1());
            entity.setZip_code2(teamDTO.getZip_code2());
            entity.setAddress(teamDTO.getAddress());
            entity.setDdd(teamDTO.getDdd());
            entity.setTel(teamDTO.getTel());
            entity.setFax(teamDTO.getFax());
            entity.setHomepage(teamDTO.getHomepage());
            entity.setOwner(teamDTO.getOwner());
            entity.setStadium_Uk(teamDTO.getStadium_Uk());
            
            Team saved = teamRepository.save(entity);
            TeamDTO dto = entityToDTO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + teamDTO.getTeam_Uk())
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 팀을 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(TeamDTO teamDTO) {
        Optional<Team> optionalEntity = teamRepository.findById(teamDTO.getId());
        if (optionalEntity.isPresent()) {
            teamRepository.deleteById(teamDTO.getId());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + teamDTO.getTeam_Uk())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 팀을 찾을 수 없습니다.")
                    .build();
        }
    }

}
