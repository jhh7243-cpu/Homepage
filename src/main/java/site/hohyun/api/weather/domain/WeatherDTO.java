package site.hohyun.api.weather.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 날씨 데이터 전송 객체
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO 
{
    private String id;                      // 아이디
    private String name;                    // 이름
    private String date;                    // 일시
    private String averageTemperature;      // 평균기온(℃)
    private String maxTemperature;          // 최고기온(℃)
    private String maxTemperatureTime;      // 최고기온시각
    private String minTemperature;          // 최저기온(℃)
    private String minTemperatureTime;      // 최저기온시각
    private String dailyTemperatureRange;   // 일교차
    private String precipitation;           // 강수량(mm)
}
