package site.aiion.api.analysis.mbti.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.analysis.mbti.domain.MbtiDTO;
import site.aiion.api.analysis.mbti.domain.Mbti;
import site.aiion.api.analysis.mbti.repository.MbtiRepository;

@Service
@RequiredArgsConstructor
public class MbtiServiceIpml implements MbtiService {

    private final MbtiRepository mbtiRepository;

    private MbtiDTO entityToDTO(Mbti entity) {
        return MbtiDTO.builder()
                .mbti_id(entity.getMbti_id())
                .m_name(entity.getM_name())
                .m_char(entity.getM_char())
                .e_i(entity.getE_i())
                .s_n(entity.getS_n())
                .t_f(entity.getT_f())
                .j_p(entity.getJ_p())
                .m_result(entity.getM_result())
                .build();
    }

    @Override
    public Messenger findById(MbtiDTO mbtiDTO) {
        Optional<Mbti> entity = mbtiRepository.findById(mbtiDTO.getMbti_id());
        if (entity.isPresent()) {
            MbtiDTO dto = entityToDTO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("MBTI를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<Mbti> entities = mbtiRepository.findAll();
        List<MbtiDTO> dtoList = entities.stream()
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
    public Messenger save(MbtiDTO mbtiDTO) {
        Mbti entity = Mbti.builder()
                .mbti_id(mbtiDTO.getMbti_id())
                .m_name(mbtiDTO.getM_name())
                .m_char(mbtiDTO.getM_char())
                .e_i(mbtiDTO.getE_i())
                .s_n(mbtiDTO.getS_n())
                .t_f(mbtiDTO.getT_f())
                .j_p(mbtiDTO.getJ_p())
                .m_result(mbtiDTO.getM_result())
                .build();
        
        Mbti saved = mbtiRepository.save(entity);
        MbtiDTO dto = entityToDTO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: ID " + saved.getMbti_id())
                .data(dto)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<MbtiDTO> mbtiDTOList) {
        List<Mbti> entities = mbtiDTOList.stream()
                .map(dto -> Mbti.builder()
                        .mbti_id(dto.getMbti_id())
                        .m_name(dto.getM_name())
                        .m_char(dto.getM_char())
                        .e_i(dto.getE_i())
                        .s_n(dto.getS_n())
                        .t_f(dto.getT_f())
                        .j_p(dto.getJ_p())
                        .m_result(dto.getM_result())
                        .build())
                .collect(Collectors.toList());
        
        List<Mbti> saved = mbtiRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(MbtiDTO mbtiDTO) {
        Optional<Mbti> optionalEntity = mbtiRepository.findById(mbtiDTO.getMbti_id());
        if (optionalEntity.isPresent()) {
            Mbti entity = optionalEntity.get();
            entity.setM_name(mbtiDTO.getM_name());
            entity.setM_char(mbtiDTO.getM_char());
            entity.setE_i(mbtiDTO.getE_i());
            entity.setS_n(mbtiDTO.getS_n());
            entity.setT_f(mbtiDTO.getT_f());
            entity.setJ_p(mbtiDTO.getJ_p());
            entity.setM_result(mbtiDTO.getM_result());
            
            Mbti saved = mbtiRepository.save(entity);
            MbtiDTO dto = entityToDTO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: ID " + mbtiDTO.getMbti_id())
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 MBTI를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(MbtiDTO mbtiDTO) {
        Optional<Mbti> optionalEntity = mbtiRepository.findById(mbtiDTO.getMbti_id());
        if (optionalEntity.isPresent()) {
            mbtiRepository.deleteById(mbtiDTO.getMbti_id());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: ID " + mbtiDTO.getMbti_id())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 MBTI를 찾을 수 없습니다.")
                    .build();
        }
    }
}

