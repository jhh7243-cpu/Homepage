package site.hohyun.api.weather.Repository;

import site.hohyun.api.weather.domain.WeatherDTO;
import site.hohyun.api.common.domain.Messenger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 메모리 기반 날씨 리포지토리
 */
@org.springframework.stereotype.Repository
public class WeatherRepository 
{
    private final Map<String, WeatherDTO> weatherStorage = new ConcurrentHashMap<>();

    /**
     * 날씨 데이터 저장
     */
    public WeatherDTO save(WeatherDTO weatherDTO) 
    {
        System.out.println("💾 WeatherRepository: 날씨 데이터 저장");
        System.out.println("   📅 날짜: " + weatherDTO.getDate());
        System.out.println("   🌡️ 평균기온: " + weatherDTO.getAverageTemperature() + "℃");
        
        String key = weatherDTO.getDate();
        if (key == null || key.isEmpty()) 
        {
            key = "weather_" + System.currentTimeMillis();
        }
        
        weatherStorage.put(key, weatherDTO);
        System.out.println("✅ WeatherRepository: 저장 완료 (총 " + weatherStorage.size() + "개)");
        
        return weatherDTO;
    }

    /**
     * 모든 날씨 데이터 조회
     */
    public List<WeatherDTO> findAll() 
    {
        System.out.println("📊 WeatherRepository: 모든 날씨 데이터 조회");
        List<WeatherDTO> weatherList = weatherStorage.values().stream()
                .collect(Collectors.toList());
        
        System.out.println("   총 " + weatherList.size() + "개의 날씨 데이터");
        
        // 10개 데이터 확인
        if (weatherList.size() == 10) 
        {
            System.out.println("✅ WeatherRepository: 10개 데이터 모두 저장 완료!");
        } 
        else 
        {
            System.out.println("⚠️ WeatherRepository: 예상과 다른 데이터 수 - " + weatherList.size() + "개");
        }
        
        // 표 형태로 데이터 출력
        if (!weatherList.isEmpty()) 
        {
            System.out.println("\n" + "=".repeat(100));
            System.out.println("                    💾 WeatherRepository: 저장된 날씨 데이터 💾");
            System.out.println("=".repeat(100));
            
            // 테이블 헤더
            System.out.println("┌────────────┬──────────┬──────────┬────────────┬──────────┬────────────┬────────┬──────────┐");
            System.out.printf("│ %-10s │ %-8s │ %-8s │ %-10s │ %-8s │ %-10s │ %-6s │ %-8s │%n",
                    "날짜", "평균기온", "최고기온", "최고기온시각", "최저기온", "최저기온시각", "일교차", "강수량");
            System.out.println("├────────────┼──────────┼──────────┼────────────┼──────────┼────────────┼────────┼──────────┤");
            
            // 데이터 출력
            for (WeatherDTO weather : weatherList) 
            {
                System.out.printf("│ %-10s │ %-8s │ %-8s │ %-10s │ %-8s │ %-10s │ %-6s │ %-8s │%n",
                        weather.getDate() != null ? weather.getDate() : "N/A",
                        weather.getAverageTemperature() != null ? weather.getAverageTemperature() + "℃" : "N/A",
                        weather.getMaxTemperature() != null ? weather.getMaxTemperature() + "℃" : "N/A",
                        weather.getMaxTemperatureTime() != null ? weather.getMaxTemperatureTime() : "N/A",
                        weather.getMinTemperature() != null ? weather.getMinTemperature() + "℃" : "N/A",
                        weather.getMinTemperatureTime() != null ? weather.getMinTemperatureTime() : "N/A",
                        weather.getDailyTemperatureRange() != null ? weather.getDailyTemperatureRange() + "℃" : "N/A",
                        weather.getPrecipitation() != null ? weather.getPrecipitation() + "mm" : "N/A");
            }
            
            System.out.println("└────────────┴──────────┴──────────┴────────────┴──────────┴────────────┴────────┴──────────┘");
            System.out.println("=".repeat(100));
            System.out.println("✅ WeatherRepository: 표 형태 출력 완료");
            System.out.println("=".repeat(100) + "\n");
        }
        
        return weatherList;
    }
    
    /**
     * 날씨 데이터 저장 상태 확인 (Messenger 반환)
     */
    public Messenger checkDataStatus() 
    {
        int totalCount = weatherStorage.size();
        String message;
        int code;
        
        if (totalCount == 0) {
            message = "📭 저장된 날씨 데이터가 없습니다.";
            code = 404;
        } else if (totalCount == 10) {
            message = "🎉 날씨 데이터 10개가 성공적으로 저장되었습니다!";
            code = 200;
        } else {
            message = "📊 현재 " + totalCount + "개의 날씨 데이터가 저장되어 있습니다.";
            code = 201;
        }
        
        return new Messenger(code, message);
    }
    
    /**
     * 날씨 데이터 저장 (Messenger 반환)
     */
    public Messenger saveWithStatus(WeatherDTO weatherDTO) 
    {
        try {
            String key = weatherDTO.getDate();
            if (key == null || key.isEmpty()) 
            {
                key = "weather_" + System.currentTimeMillis();
            }
            
            weatherStorage.put(key, weatherDTO);
            
            return new Messenger(200, "날씨 데이터가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return new Messenger(500, "날씨 데이터 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 모든 날씨 데이터 조회 (Messenger 반환)
     */
    public Messenger findAllWithStatus() 
    {
        try {
            List<WeatherDTO> weatherList = weatherStorage.values().stream()
                    .collect(Collectors.toList());
            
            if (weatherList.isEmpty()) {
                return new Messenger(404, "저장된 날씨 데이터가 없습니다.");
            } else {
                return new Messenger(200, "총 " + weatherList.size() + "개의 날씨 데이터를 조회했습니다.");
            }
        } catch (Exception e) {
            return new Messenger(500, "날씨 데이터 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 날씨 데이터 삭제 (Messenger 반환)
     */
    public Messenger deleteWithStatus(String date) 
    {
        try {
            WeatherDTO removed = weatherStorage.remove(date);
            if (removed != null) {
                return new Messenger(200, "날씨 데이터가 성공적으로 삭제되었습니다.");
            } else {
                return new Messenger(404, "삭제할 날씨 데이터를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return new Messenger(500, "날씨 데이터 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 날씨 데이터 업데이트 (Messenger 반환)
     */
    public Messenger updateWithStatus(WeatherDTO weatherDTO) 
    {
        try {
            String key = weatherDTO.getDate();
            if (weatherStorage.containsKey(key)) {
                weatherStorage.put(key, weatherDTO);
                return new Messenger(200, "날씨 데이터가 성공적으로 업데이트되었습니다.");
            } else {
                return new Messenger(404, "업데이트할 날씨 데이터를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return new Messenger(500, "날씨 데이터 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 날씨 데이터 일괄 저장 (Messenger 반환)
     */
    public Messenger saveAllWithStatus(List<WeatherDTO> weatherDTOList) 
    {
        try {
            for (WeatherDTO weatherDTO : weatherDTOList) {
                String key = weatherDTO.getDate();
                if (key == null || key.isEmpty()) {
                    key = "weather_" + System.currentTimeMillis();
                }
                weatherStorage.put(key, weatherDTO);
            }
            return new Messenger(200, "총 " + weatherDTOList.size() + "개의 날씨 데이터가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return new Messenger(500, "날씨 데이터 일괄 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}