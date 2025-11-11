package site.aiion.api.analysis.mbti.service;

import site.aiion.api.common.domain.Messenger;

public interface MbtiService {
    Messenger findById(Long id);
    Messenger findAll();
}
