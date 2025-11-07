package site.aiion.api.analysis.bigfive.service;

import java.util.List;
import site.aiion.api.analysis.bigfive.domain.BigfiveDTO;
import site.aiion.api.common.domain.Messenger;

public interface BigfiveService {
    public Messenger findById(BigfiveDTO bigfiveDTO);
    public Messenger findAll();
    public Messenger save(BigfiveDTO bigfiveDTO);
    public Messenger saveAll(List<BigfiveDTO> bigfiveDTOList);
    public Messenger update(BigfiveDTO bigfiveDTO);
    public Messenger delete(BigfiveDTO bigfiveDTO);
}

