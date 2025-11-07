-- Team INSERT 문
-- 테이블명: teams
-- id는 자동 생성되므로 제외
-- stadium_uk는 Stadium 테이블의 stadium_uk 값을 참조

INSERT INTO teams (
    team_uk,
    region_name,
    team_name,
    e_team_name,
    orig_yyyy,
    zip_code1,
    zip_code2,
    address,
    ddd,
    tel,
    fax,
    homepage,
    owner,
    stadium_uk
) VALUES
    ('K01', '서울', 'FC서울', 'FC Seoul', '1983', '02777', '', '서울특별시 송파구 올림픽로 240', '02', '2230-5100', '02-2230-5109', 'http://www.fcseoul.com', 'GS에너지', 'D01'),
    ('K02', '성남', '성남 FC', 'Seongnam FC', '1989', '13509', '', '경기도 성남시 중원구 성남대로 717', '031', '729-8551', '031-729-8559', 'http://www.seongnamfc.com', '성남시청', 'D02'),
    ('K03', '포항', '포항 스틸러스', 'Pohang Steelers', '1973', '790-050', '', '경상북도 포항시 남구 동해안로6213번길 20', '054', '212-2011', '054-212-2019', 'http://www.steelers.co.kr', '포스코', 'D03'),
    ('K04', '인천', '인천 유나이티드', 'Incheon United', '2003', '22006', '', '인천광역시 연수구 송도과학로 123', '032', '777-0010', '032-777-0019', 'http://www.incheonutd.com', '인천광역시', 'D04'),
    ('K05', '전북', '전북 현대 모터스', 'Jeonbuk Hyundai Motors', '1994', '54878', '', '전라북도 전주시 덕진구 반월동 763-1', '063', '273-1763', '063-273-1769', 'http://www.hyundai-motorsfc.com', '현대자동차', 'D05');

