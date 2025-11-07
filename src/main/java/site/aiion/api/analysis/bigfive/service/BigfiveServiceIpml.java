package site.aiion.api.analysis.bigfive.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.analysis.bigfive.domain.BigfiveDTO;
import site.aiion.api.analysis.bigfive.domain.Bigfive;
import site.aiion.api.analysis.bigfive.repository.BigfiveRepository;

@Service
@RequiredArgsConstructor
public class BigfiveServiceIpml implements BigfiveService {

    private final BigfiveRepository bigfiveRepository;

    private BigfiveDTO entityToDTO(Bigfive entity) {
        return BigfiveDTO.builder()
                .id(entity.getId())
                .five_name(entity.getFive_name())
                .f_level(entity.getF_level())
                .f_point(entity.getF_point())
                .f_action(entity.getF_action())
                .build();
    }

    @Override
    public Messenger findById(BigfiveDTO bigfiveDTO) {
        Optional<Bigfive> entity = bigfiveRepository.findById(bigfiveDTO.getId());
        if (entity.isPresent()) {
            BigfiveDTO dto = entityToDTO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("빅파이브를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<Bigfive> entities = bigfiveRepository.findAll();
        List<BigfiveDTO> dtoList = entities.stream()
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
    public Messenger save(BigfiveDTO bigfiveDTO) {
        Bigfive entity = Bigfive.builder()
                .five_name(bigfiveDTO.getFive_name())
                .f_level(bigfiveDTO.getF_level())
                .f_point(bigfiveDTO.getF_point())
                .f_action(bigfiveDTO.getF_action())
                .build();
        
        Bigfive saved = bigfiveRepository.save(entity);
        BigfiveDTO dto = entityToDTO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: ID " + saved.getId())
                .data(dto)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<BigfiveDTO> bigfiveDTOList) {
        List<Bigfive> entities = bigfiveDTOList.stream()
                .map(dto -> Bigfive.builder()
                        .five_name(dto.getFive_name())
                        .f_level(dto.getF_level())
                        .f_point(dto.getF_point())
                        .f_action(dto.getF_action())
                        .build())
                .collect(Collectors.toList());
        
        List<Bigfive> saved = bigfiveRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(BigfiveDTO bigfiveDTO) {
        Optional<Bigfive> optionalEntity = bigfiveRepository.findById(bigfiveDTO.getId());
        if (optionalEntity.isPresent()) {
            Bigfive entity = optionalEntity.get();
            entity.setFive_name(bigfiveDTO.getFive_name());
            entity.setF_level(bigfiveDTO.getF_level());
            entity.setF_point(bigfiveDTO.getF_point());
            entity.setF_action(bigfiveDTO.getF_action());
            
            Bigfive saved = bigfiveRepository.save(entity);
            BigfiveDTO dto = entityToDTO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: ID " + bigfiveDTO.getId())
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 빅파이브를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(BigfiveDTO bigfiveDTO) {
        Optional<Bigfive> optionalEntity = bigfiveRepository.findById(bigfiveDTO.getId());
        if (optionalEntity.isPresent()) {
            bigfiveRepository.deleteById(bigfiveDTO.getId());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: ID " + bigfiveDTO.getId())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 빅파이브를 찾을 수 없습니다.")
                    .build();
        }
    }
}

