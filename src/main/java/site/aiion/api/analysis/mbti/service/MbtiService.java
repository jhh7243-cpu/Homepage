package site.aiion.api.analysis.mbti.service;

import java.util.List;
import site.aiion.api.analysis.mbti.domain.MbtiDTO;
import site.aiion.api.common.domain.Messenger;

public interface MbtiService {
    public Messenger findById(MbtiDTO mbtiDTO);
    public Messenger findAll();
    public Messenger save(MbtiDTO mbtiDTO);
    public Messenger saveAll(List<MbtiDTO> mbtiDTOList);
    public Messenger update(MbtiDTO mbtiDTO);
    public Messenger delete(MbtiDTO mbtiDTO);
}

