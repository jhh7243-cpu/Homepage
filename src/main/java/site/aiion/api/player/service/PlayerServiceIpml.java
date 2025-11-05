package site.aiion.api.player.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.player.domain.PlayerDTO;
import site.aiion.api.player.domain.Player;
import site.aiion.api.player.repository.PlayerRepository;

@Service
@RequiredArgsConstructor
public class PlayerServiceIpml implements PlayerService {

    private final PlayerRepository playerRepository;

    private PlayerDTO entityToDTO(Player entity) {
        return PlayerDTO.builder()
                .id(entity.getId())
                .player_Uk(entity.getPlayer_Uk())
                .player_name(entity.getPlayer_name())
                .e_player_name(entity.getE_player_name())
                .nickname(entity.getNickname())
                .join_yyyy(entity.getJoin_yyyy())
                .position(entity.getPosition())
                .back_no(entity.getBack_no())
                .nation(entity.getNation())
                .birth_date(entity.getBirth_date())
                .solar(entity.getSolar())
                .height(entity.getHeight())
                .weight(entity.getWeight())
                .team_Uk(entity.getTeam_Uk())
                .build();
    }

    @Override
    public Messenger findById(PlayerDTO playerDTO) {
        Optional<Player> entity = playerRepository.findById(playerDTO.getId());
        if (entity.isPresent()) {
            PlayerDTO dto = entityToDTO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("선수를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<Player> entities = playerRepository.findAll();
        List<PlayerDTO> dtoList = entities.stream()
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
    public Messenger save(PlayerDTO playerDTO) {
        Player entity = Player.builder()
                .player_Uk(playerDTO.getPlayer_Uk())
                .player_name(playerDTO.getPlayer_name())
                .e_player_name(playerDTO.getE_player_name())
                .nickname(playerDTO.getNickname())
                .join_yyyy(playerDTO.getJoin_yyyy())
                .position(playerDTO.getPosition())
                .back_no(playerDTO.getBack_no())
                .nation(playerDTO.getNation())
                .birth_date(playerDTO.getBirth_date())
                .solar(playerDTO.getSolar())
                .height(playerDTO.getHeight())
                .weight(playerDTO.getWeight())
                .team_Uk(playerDTO.getTeam_Uk())
                .build();
        
        Player saved = playerRepository.save(entity);
        PlayerDTO dto = entityToDTO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.getPlayer_Uk())
                .data(dto)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<PlayerDTO> playerDTOList) {
        List<Player> entities = playerDTOList.stream()
                .map(dto -> Player.builder()
                        .player_Uk(dto.getPlayer_Uk())
                        .player_name(dto.getPlayer_name())
                        .e_player_name(dto.getE_player_name())
                        .nickname(dto.getNickname())
                        .join_yyyy(dto.getJoin_yyyy())
                        .position(dto.getPosition())
                        .back_no(dto.getBack_no())
                        .nation(dto.getNation())
                        .birth_date(dto.getBirth_date())
                        .solar(dto.getSolar())
                        .height(dto.getHeight())
                        .weight(dto.getWeight())
                        .team_Uk(dto.getTeam_Uk())
                        .build())
                .collect(Collectors.toList());
        
        List<Player> saved = playerRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(PlayerDTO playerDTO) {
        Optional<Player> optionalEntity = playerRepository.findById(playerDTO.getId());
        if (optionalEntity.isPresent()) {
            Player entity = optionalEntity.get();
            entity.setPlayer_Uk(playerDTO.getPlayer_Uk());
            entity.setPlayer_name(playerDTO.getPlayer_name());
            entity.setE_player_name(playerDTO.getE_player_name());
            entity.setNickname(playerDTO.getNickname());
            entity.setJoin_yyyy(playerDTO.getJoin_yyyy());
            entity.setPosition(playerDTO.getPosition());
            entity.setBack_no(playerDTO.getBack_no());
            entity.setNation(playerDTO.getNation());
            entity.setBirth_date(playerDTO.getBirth_date());
            entity.setSolar(playerDTO.getSolar());
            entity.setHeight(playerDTO.getHeight());
            entity.setWeight(playerDTO.getWeight());
            entity.setTeam_Uk(playerDTO.getTeam_Uk());
            
            Player saved = playerRepository.save(entity);
            PlayerDTO dto = entityToDTO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + playerDTO.getPlayer_Uk())
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 선수를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(PlayerDTO playerDTO) {
        Optional<Player> optionalEntity = playerRepository.findById(playerDTO.getId());
        if (optionalEntity.isPresent()) {
            playerRepository.deleteById(playerDTO.getId());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + playerDTO.getPlayer_Uk())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 선수를 찾을 수 없습니다.")
                    .build();
        }
    }

}
