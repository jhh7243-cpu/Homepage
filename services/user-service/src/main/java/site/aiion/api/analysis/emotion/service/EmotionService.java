package site.aiion.api.analysis.emotion.service;

import java.util.List;
import site.aiion.api.analysis.emotion.domain.EmotionDTO;
import site.aiion.api.common.domain.Messenger;

public interface EmotionService {
    public Messenger findById(EmotionDTO emotionDTO);
    public Messenger findAll();
    public Messenger save(EmotionDTO emotionDTO);
    public Messenger saveAll(List<EmotionDTO> emotionDTOList);
    public Messenger update(EmotionDTO emotionDTO);
    public Messenger delete(EmotionDTO emotionDTO);
}

