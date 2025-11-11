package site.aiion.api.analysis.bigfive.service;

import site.aiion.api.common.domain.Messenger;

public interface BigfiveService {
    Messenger findById(Long id);
    Messenger findAll();
}
