-- Stadium 엔티티 스키마에 맞는 INSERT 문
-- id는 @GeneratedValue로 자동 생성되므로 제외
-- seat_count는 String 타입이므로 따옴표로 감싸야 함

INSERT INTO stadium (
    stadium_id, 
    stadium_name, 
    hometeam_id, 
    seat_count, 
    address, 
    ddd, 
    tel
) VALUES
    ('D03', '전주월드컵경기장', 'K05', '28000', '전북 전주시 덕진구 반월동 763-1', '063', '273-1763');

