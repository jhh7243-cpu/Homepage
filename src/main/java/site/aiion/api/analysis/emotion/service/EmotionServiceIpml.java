package site.aiion.api.analysis.emotion.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.analysis.emotion.domain.EmotionDTO;
import site.aiion.api.analysis.emotion.domain.Emotion;
import site.aiion.api.analysis.emotion.repository.EmotionRepository;

@Service
@RequiredArgsConstructor
public class EmotionServiceIpml implements EmotionService {

    private final EmotionRepository emotionRepository;

    private EmotionDTO entityToDTO(Emotion entity) {
        return EmotionDTO.builder()
                .id(entity.getId())
                .joy(entity.getJoy())
                .sadness(entity.getSadness())
                .anger(entity.getAnger())
                .fear(entity.getFear())
                .disgust(entity.getDisgust())
                .surprise(entity.getSurprise())
                .sentiment_score(entity.getSentiment_score())
                .dominant_emotion(entity.getDominant_emotion())
                .analyzed_at(entity.getAnalyzed_at())
                .build();
    }

    @Override
    public Messenger findById(EmotionDTO emotionDTO) {
        Optional<Emotion> entity = emotionRepository.findById(emotionDTO.getId());
        if (entity.isPresent()) {
            EmotionDTO dto = entityToDTO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("감정을 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<Emotion> entities = emotionRepository.findAll();
        List<EmotionDTO> dtoList = entities.stream()
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
    public Messenger save(EmotionDTO emotionDTO) {
        Emotion entity = Emotion.builder()
                .joy(emotionDTO.getJoy())
                .sadness(emotionDTO.getSadness())
                .anger(emotionDTO.getAnger())
                .fear(emotionDTO.getFear())
                .disgust(emotionDTO.getDisgust())
                .surprise(emotionDTO.getSurprise())
                .sentiment_score(emotionDTO.getSentiment_score())
                .dominant_emotion(emotionDTO.getDominant_emotion())
                .analyzed_at(emotionDTO.getAnalyzed_at() != null ? emotionDTO.getAnalyzed_at() : java.time.LocalDateTime.now())
                .build();
        
        Emotion saved = emotionRepository.save(entity);
        EmotionDTO dto = entityToDTO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: ID " + saved.getId())
                .data(dto)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<EmotionDTO> emotionDTOList) {
        List<Emotion> entities = emotionDTOList.stream()
                .map(dto -> Emotion.builder()
                        .joy(dto.getJoy())
                        .sadness(dto.getSadness())
                        .anger(dto.getAnger())
                        .fear(dto.getFear())
                        .disgust(dto.getDisgust())
                        .surprise(dto.getSurprise())
                        .sentiment_score(dto.getSentiment_score())
                        .dominant_emotion(dto.getDominant_emotion())
                        .analyzed_at(dto.getAnalyzed_at() != null ? dto.getAnalyzed_at() : java.time.LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        
        List<Emotion> saved = emotionRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(EmotionDTO emotionDTO) {
        Optional<Emotion> optionalEntity = emotionRepository.findById(emotionDTO.getId());
        if (optionalEntity.isPresent()) {
            Emotion entity = optionalEntity.get();
            entity.setJoy(emotionDTO.getJoy());
            entity.setSadness(emotionDTO.getSadness());
            entity.setAnger(emotionDTO.getAnger());
            entity.setFear(emotionDTO.getFear());
            entity.setDisgust(emotionDTO.getDisgust());
            entity.setSurprise(emotionDTO.getSurprise());
            entity.setSentiment_score(emotionDTO.getSentiment_score());
            entity.setDominant_emotion(emotionDTO.getDominant_emotion());
            entity.setAnalyzed_at(emotionDTO.getAnalyzed_at());
            
            Emotion saved = emotionRepository.save(entity);
            EmotionDTO dto = entityToDTO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: ID " + emotionDTO.getId())
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 감정을 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(EmotionDTO emotionDTO) {
        Optional<Emotion> optionalEntity = emotionRepository.findById(emotionDTO.getId());
        if (optionalEntity.isPresent()) {
            emotionRepository.deleteById(emotionDTO.getId());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: ID " + emotionDTO.getId())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 감정을 찾을 수 없습니다.")
                    .build();
        }
    }
}

