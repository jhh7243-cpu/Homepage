package site.aiion.api.analysis.mbti.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.aiion.api.analysis.mbti.domain.MbtiDTO;
import site.aiion.api.analysis.mbti.repository.MbtiRepository;
import site.aiion.api.common.domain.Messenger;

@Service
@RequiredArgsConstructor
public class MbtiServiceIpml implements MbtiService {

    private final MbtiRepository mbtiRepository;

    @Override
    public Messenger findById(Long id) {
        if (id == null) {
            return Messenger.builder()
                    .Code(400)
                    .message("ID는 필수 값입니다.")
                    .build();
        }

        return mbtiRepository.findById(id)
                .map(entity -> Messenger.builder()
                        .Code(200)
                        .message("조회 성공")
                        .data(MbtiDTO.of(entity))
                        .build())
                .orElseGet(() -> Messenger.builder()
                        .Code(404)
                        .message("MBTI를 찾을 수 없습니다.")
                        .build());
    }

    @Override
    public Messenger findAll() {
        var dtoList = mbtiRepository.findAll()
                .stream()
                .map(MbtiDTO::of)
                .collect(Collectors.toList());

        return Messenger.builder()
                .Code(200)
                .message("전체 조회 성공: " + dtoList.size() + "개")
                .data(dtoList)
                .build();
    }
}

    

