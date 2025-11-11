package site.aiion.api.analysis.bigfive.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.aiion.api.analysis.bigfive.domain.Bigfive;
import site.aiion.api.analysis.bigfive.domain.BigfiveDTO;
import site.aiion.api.analysis.bigfive.repository.BigfiveRepository;
import site.aiion.api.common.domain.Messenger;

@Service
@RequiredArgsConstructor
public class BigfiveServiceIpml implements BigfiveService {

    private final BigfiveRepository bigfiveRepository;

    @Override
    public Messenger findById(Long id) {
        if (id == null) {
            return Messenger.builder()
                    .Code(400)
                    .message("ID는 필수 값입니다.")
                    .build();
        }

        Optional<Bigfive> entity = bigfiveRepository.findById(id);
        if (entity.isEmpty()) {
            return Messenger.builder()
                    .Code(404)
                    .message("빅파이브를 찾을 수 없습니다.")
                    .build();
        }

        return Messenger.builder()
                .Code(200)
                .message("조회 성공")
                .data(BigfiveDTO.of(entity.get()))
                .build();
    }

    @Override
    public Messenger findAll() {
        List<BigfiveDTO> dtoList = bigfiveRepository.findAll()
                .stream()
                .map(BigfiveDTO::of)
                .collect(Collectors.toList());

        return Messenger.builder()
                .Code(200)
                .message("전체 조회 성공: " + dtoList.size() + "개")
                .data(dtoList)
                .build();
    }
}
