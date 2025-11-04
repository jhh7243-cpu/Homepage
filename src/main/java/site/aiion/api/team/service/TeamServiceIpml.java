package site.aiion.api.team.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.team.domain.TeamDTO;
import site.aiion.api.team.domain.TeamEntity;
import site.aiion.api.team.domain.TeamVO;
import site.aiion.api.team.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamServiceIpml implements TeamService {

    private final TeamRepository teamRepository;

    private TeamVO entityToVO(TeamEntity entity) {
        return TeamVO.builder()
                .teamId(entity.getTeamId())
                .regionName(entity.getRegionName())
                .teamName(entity.getTeamName())
                .eTeamName(entity.getETeamName())
                .origYyyy(entity.getOrigYyyy())
                .zipCode1(entity.getZipCode1())
                .zipCode2(entity.getZipCode2())
                .address(entity.getAddress())
                .ddd(entity.getDdd())
                .tel(entity.getTel())
                .fax(entity.getFax())
                .homepage(entity.getHomepage())
                .owner(entity.getOwner())
                .stadiumId(entity.getStadiumId())
                .build();
    }

    @Override
    public Messenger findById(TeamDTO teamDTO) {
        Optional<TeamEntity> entity = teamRepository.findById(teamDTO.getTeamId());
        if (entity.isPresent()) {
            TeamVO vo = entityToVO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(vo)
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
        List<TeamEntity> entities = teamRepository.findAll();
        List<TeamVO> voList = entities.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
        return Messenger.builder()
                .Code(200)
                .message("전체 조회 성공: " + voList.size() + "개")
                .data(voList)
                .build();
    }

    @Override
    @Transactional
    public Messenger save(TeamDTO teamDTO) {
        TeamEntity entity = TeamEntity.builder()
                .teamId(teamDTO.getTeamId())
                .regionName(teamDTO.getRegionName())
                .teamName(teamDTO.getTeamName())
                .eTeamName(teamDTO.getETeamName())
                .origYyyy(teamDTO.getOrigYyyy())
                .zipCode1(teamDTO.getZipCode1())
                .zipCode2(teamDTO.getZipCode2())
                .address(teamDTO.getAddress())
                .ddd(teamDTO.getDdd())
                .tel(teamDTO.getTel())
                .fax(teamDTO.getFax())
                .homepage(teamDTO.getHomepage())
                .owner(teamDTO.getOwner())
                .stadiumId(teamDTO.getStadiumId())
                .build();
        
        TeamEntity saved = teamRepository.save(entity);
        TeamVO vo = entityToVO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.getTeamId())
                .data(vo)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<TeamDTO> teamDTOList) {
        List<TeamEntity> entities = teamDTOList.stream()
                .map(dto -> TeamEntity.builder()
                        .teamId(dto.getTeamId())
                        .regionName(dto.getRegionName())
                        .teamName(dto.getTeamName())
                        .eTeamName(dto.getETeamName())
                        .origYyyy(dto.getOrigYyyy())
                        .zipCode1(dto.getZipCode1())
                        .zipCode2(dto.getZipCode2())
                        .address(dto.getAddress())
                        .ddd(dto.getDdd())
                        .tel(dto.getTel())
                        .fax(dto.getFax())
                        .homepage(dto.getHomepage())
                        .owner(dto.getOwner())
                        .stadiumId(dto.getStadiumId())
                        .build())
                .collect(Collectors.toList());
        
        List<TeamEntity> saved = teamRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(TeamDTO teamDTO) {
        Optional<TeamEntity> optionalEntity = teamRepository.findById(teamDTO.getTeamId());
        if (optionalEntity.isPresent()) {
            TeamEntity entity = optionalEntity.get();
            entity.setRegionName(teamDTO.getRegionName());
            entity.setTeamName(teamDTO.getTeamName());
            entity.setETeamName(teamDTO.getETeamName());
            entity.setOrigYyyy(teamDTO.getOrigYyyy());
            entity.setZipCode1(teamDTO.getZipCode1());
            entity.setZipCode2(teamDTO.getZipCode2());
            entity.setAddress(teamDTO.getAddress());
            entity.setDdd(teamDTO.getDdd());
            entity.setTel(teamDTO.getTel());
            entity.setFax(teamDTO.getFax());
            entity.setHomepage(teamDTO.getHomepage());
            entity.setOwner(teamDTO.getOwner());
            entity.setStadiumId(teamDTO.getStadiumId());
            
            TeamEntity saved = teamRepository.save(entity);
            TeamVO vo = entityToVO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + teamDTO.getTeamId())
                    .data(vo)
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
        Optional<TeamEntity> optionalEntity = teamRepository.findById(teamDTO.getTeamId());
        if (optionalEntity.isPresent()) {
            teamRepository.deleteById(teamDTO.getTeamId());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + teamDTO.getTeamId())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 팀을 찾을 수 없습니다.")
                    .build();
        }
    }

}
