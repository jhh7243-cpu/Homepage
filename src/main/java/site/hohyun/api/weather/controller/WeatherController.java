package site.hohyun.api.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import site.hohyun.api.weather.service.WeatherService;
import site.hohyun.api.weather.domain.WeatherDTO;
import site.hohyun.api.common.domain.Messenger;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import lombok.RequiredArgsConstructor;

/**
 * 날씨 RESTful API 컨트롤러
 */
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController
{
    private final WeatherService weatherService;

    /**
     * 모든 날씨 데이터 조회
     * GET /api/weather
     */
    @GetMapping("")
    public ResponseEntity<?> getAllWeather() 
    {
        try {
            List<WeatherDTO> weatherList = weatherService.getAllWeatherData();
            Messenger statusMessage = weatherService.getAllWeatherDataWithStatus();
            
            return ResponseEntity.ok()
                .body(new WeatherResponse(weatherList, statusMessage, statusMessage.getMessage()));
        } catch (Exception e) {
            System.err.println("❌ WeatherController: 날씨 데이터 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("날씨 데이터 조회 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    /**
     * 테스트용 날씨 데이터 생성 및 저장 (10개)
     * POST /api/weather/test
     */
    @PostMapping("/test")
    public ResponseEntity<?> createTestWeather() 
    {
        try {
            System.out.println("🌤️ WeatherController: 테스트 데이터 생성 시작");
            
            // 10개 WeatherDTO 생성 및 저장
            List<WeatherDTO> testWeatherList = new ArrayList<>();
            for (int i = 1; i <= 10; i++) 
            {
                WeatherDTO weatherDTO = new WeatherDTO();
                weatherDTO.setDate("2024-01-" + String.format("%02d", i));
                weatherDTO.setAverageTemperature(String.valueOf(10 + i));
                weatherDTO.setMaxTemperature(String.valueOf(15 + i));
                weatherDTO.setMinTemperature(String.valueOf(5 + i));
                weatherDTO.setPrecipitation(String.valueOf(i * 0.5));
                weatherDTO.setDailyTemperatureRange("10.0");
                weatherDTO.setMaxTemperatureTime("14:00");
                weatherDTO.setMinTemperatureTime("06:00");
                
                testWeatherList.add(weatherDTO);
            }
            
            // WeatherServiceIpml을 통해 WeatherRepository로 전송
            System.out.println("📤 WeatherController: WeatherServiceIpml로 " + testWeatherList.size() + "개 데이터 전송");
            Messenger saveStatusMessage = weatherService.saveAllWeatherWithStatus(testWeatherList);
            System.out.println("📨 WeatherController: WeatherServiceIpml에서 받은 저장 상태: " + saveStatusMessage.getMessage());
            
            // WeatherServiceIpml을 통해 WeatherRepository에서 데이터 조회
            System.out.println("📥 WeatherController: WeatherServiceIpml을 통해 데이터 조회 요청");
            List<WeatherDTO> allWeather = weatherService.getAllWeatherData();
            Messenger statusMessage = weatherService.checkWeatherDataStatus();
            System.out.println("📨 WeatherController: WeatherServiceIpml에서 받은 조회 상태: " + statusMessage.getMessage());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new WeatherResponse(allWeather, statusMessage, 
                    "WeatherRepository → WeatherServiceIpml → WeatherController 데이터 흐름 완료! " + statusMessage.getMessage()));
        } catch (Exception e) {
            System.err.println("❌ WeatherController: 테스트 데이터 생성 중 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("테스트 데이터 생성 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    /**
     * 날씨 데이터 저장
     * POST /api/weather
     */
    @PostMapping("")
    public ResponseEntity<?> createWeather(@RequestBody WeatherDTO weatherDTO) 
    {
        try {
            Messenger statusMessage = weatherService.saveWeatherWithStatus(weatherDTO);
            List<WeatherDTO> weatherList = weatherService.getAllWeatherData();
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new WeatherResponse(weatherList, statusMessage, statusMessage.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("날씨 데이터 저장 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    /**
     * 날씨 데이터 개수 조회
     * GET /api/weather/count
     */
    @GetMapping("/count")
    public ResponseEntity<?> getWeatherCount() 
    {
        try {
            int count = weatherService.getWeatherDataCount();
            List<WeatherDTO> weatherList = weatherService.getAllWeatherData();
            Messenger statusMessage = weatherService.getAllWeatherDataWithStatus();
            
            return ResponseEntity.ok()
                .body(new WeatherResponse(weatherList, statusMessage, 
                    "총 " + count + "개의 날씨 데이터가 있습니다. " + statusMessage.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("날씨 데이터 개수 조회 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    /**
     * 날씨 데이터 상태 확인
     * GET /api/weather/status
     */
    @GetMapping("/status")
    public ResponseEntity<?> getWeatherStatus() 
    {
        try {
            weatherService.printWeatherDataStatus();
            List<WeatherDTO> weatherList = weatherService.getAllWeatherData();
            Messenger statusMessage = weatherService.checkWeatherDataStatus();
            
            return ResponseEntity.ok()
                .body(new WeatherResponse(weatherList, statusMessage, 
                    "날씨 데이터 상태를 콘솔에 출력했습니다. " + statusMessage.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("날씨 데이터 상태 출력 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    /**
     * 날씨 데이터 리스트 출력
     * GET /api/weather/print
     */
    @GetMapping("/print")
    public ResponseEntity<?> printWeatherList() 
    {
        try {
            List<WeatherDTO> weatherList = weatherService.getAllWeatherData();
            weatherService.printWeatherDataList(weatherList);
            Messenger statusMessage = weatherService.getAllWeatherDataWithStatus();
            
            return ResponseEntity.ok()
                .body(new WeatherResponse(weatherList, statusMessage, 
                    "날씨 데이터 리스트를 콘솔에 출력했습니다. " + statusMessage.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("날씨 데이터 리스트 출력 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    /**
     * 날씨 데이터 수신 확인
     * GET /api/weather/check
     */
    @GetMapping("/check")
    public ResponseEntity<?> checkWeatherData() 
    {
        try {
            weatherService.checkWeatherDataReceived();
            List<WeatherDTO> weatherList = weatherService.getAllWeatherData();
            Messenger statusMessage = weatherService.checkWeatherDataStatus();
            
            return ResponseEntity.ok()
                .body(new WeatherResponse(weatherList, statusMessage, 
                    "날씨 데이터 수신 상태를 확인했습니다. " + statusMessage.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("날씨 데이터 수신 확인 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    /**
     * TRAIN_weather.csv 파일에서 데이터 로드 및 저장
     * POST /api/weather/csv
     */
    @PostMapping("/csv")
    public ResponseEntity<?> loadWeatherCSV() 
    {
        List<WeatherDTO> weatherList = new ArrayList<>();
        
        try {
            // CSV 파일 경로
            Resource resource = new ClassPathResource("static/csv/TRAIN_weather.csv-Grid view.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            
            // CSV 파서 생성 (헤더 포함)
            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(bufferedReader);
            
            int recordCount = 0;
            
            // CSV 레코드를 ArrayList에 저장
            for (CSVRecord record : csvParser) {
                WeatherDTO weatherDTO = new WeatherDTO();
                
                // CSV 헤더를 사용하여 데이터 매핑
                weatherDTO.setDate(record.get("일시"));                                    // 일시
                weatherDTO.setAverageTemperature(record.get("평균기온(℃)"));              // 평균기온
                weatherDTO.setMaxTemperature(record.get("최고기온(℃)"));                  // 최고기온
                weatherDTO.setMaxTemperatureTime(record.get("최고기온시각"));              // 최고기온시각
                weatherDTO.setMinTemperature(record.get("최저기온(℃)"));                  // 최저기온
                weatherDTO.setMinTemperatureTime(record.get("최저기온시각"));              // 최저기온시각
                weatherDTO.setDailyTemperatureRange(record.get("일교차"));                 // 일교차
                weatherDTO.setPrecipitation(record.get("강수량(mm)"));                   // 강수량
                
                // ArrayList에 날씨 데이터 추가
                weatherList.add(weatherDTO);
                recordCount++;
            }
            
            // 리소스 정리
            csvParser.close();
            bufferedReader.close();
            
            // Service로 전체 리스트 전송 (Messenger 반환)
            Messenger saveStatusMessage = weatherService.saveAllWeatherWithStatus(weatherList);
            
            // 저장된 데이터 조회
            List<WeatherDTO> allWeather = weatherService.getAllWeatherData();
            Messenger statusMessage = weatherService.checkWeatherDataStatus();
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new WeatherResponse(allWeather, statusMessage, 
                    "TRAIN_weather.csv 파일에서 " + recordCount + "개 데이터 로드 완료! " + saveStatusMessage.getMessage() + " " + statusMessage.getMessage()));
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("CSV 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    // 응답 클래스들
    public static class WeatherResponse {
        private List<WeatherDTO> weatherDataList;
        private Messenger statusMessage;
        private String message;
        
        public WeatherResponse(List<WeatherDTO> weatherDataList, Messenger statusMessage, String message) {
            this.weatherDataList = weatherDataList;
            this.statusMessage = statusMessage;
            this.message = message;
        }
        
        // Getters and Setters
        public List<WeatherDTO> getWeatherDataList() { return weatherDataList; }
        public void setWeatherDataList(List<WeatherDTO> weatherDataList) { this.weatherDataList = weatherDataList; }
        
        public Messenger getStatusMessage() { return statusMessage; }
        public void setStatusMessage(Messenger statusMessage) { this.statusMessage = statusMessage; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
    
    public static class ErrorResponse {
        private String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
        
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
    }

}
