package site.hohyun.api.weather.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherVO 
{
    private String date;                    // 일시
    private String averageTemperature;      // 평균기온(℃)
    private String maxTemperature;          // 최고기온(℃)
    private String maxTemperatureTime;      // 최고기온시각
    private String minTemperature;          // 최저기온(℃)
    private String minTemperatureTime;      // 최저기온시각
    private String dailyTemperatureRange;   // 일교차
    private String precipitation;           // 강수량(mm)
    
}
