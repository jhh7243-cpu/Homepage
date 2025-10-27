package site.hohyun.api.weather.service;

import java.util.List;

import org.springframework.stereotype.Service;
import site.hohyun.api.weather.Repository.WeatherRepository;
import site.hohyun.api.weather.domain.WeatherDTO;
import site.hohyun.api.common.domain.Messenger;

@Service
public class WeatherServiceIpml 
implements WeatherService 
{
    private final WeatherRepository weatherRepository;

    public WeatherServiceIpml(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    /**
     * 단일 날씨 데이터 저장
     */
    @Override
    public void saveWeather(WeatherDTO weatherDTO) {
        weatherRepository.save(weatherDTO);
    }

    /**
     * 날씨 데이터 일괄 저장
     */
    @Override
    public void saveAll(List<WeatherDTO> weatherDTOList) 
    {
        for (WeatherDTO weatherDTO : weatherDTOList) {
            weatherRepository.save(weatherDTO);
        }
    }

    /**
     * 모든 날씨 데이터 조회
     */
    @Override
    public List<WeatherDTO> getAllWeatherData() 
    {
        System.out.println("📥 WeatherServiceIpml: WeatherController로부터 데이터 조회 요청 수신");
        System.out.println("📤 WeatherServiceIpml: WeatherRepository로 조회 요청 전송");
        List<WeatherDTO> result = weatherRepository.findAll();
        System.out.println("📨 WeatherServiceIpml: WeatherRepository에서 " + result.size() + "개 데이터 수신");
        System.out.println("📤 WeatherServiceIpml: WeatherController로 데이터 전송");
        return result;
    }

    /**
     * 날씨 데이터 개수 조회
     */
    @Override
    public int getWeatherDataCount() {
        return weatherRepository.findAll().size();
    }

    /**
     * 날씨 데이터 상태 출력
     */
    @Override
    public void printWeatherDataStatus() 
    {
        System.out.println("🌤️ WeatherService: 날씨 데이터 상태 확인");
        List<WeatherDTO> weatherList = weatherRepository.findAll();
        System.out.println("📊 WeatherService: 총 " + weatherList.size() + "개의 날씨 데이터");
    }

    /**
     * 날씨 데이터 목록을 터미널에 출력
     */
    @Override
    public void printWeatherDataList(List<WeatherDTO> weatherDataList) 
    {
        System.out.println("📋 WeatherService: 날씨 데이터 목록 출력");
        if (weatherDataList == null || weatherDataList.isEmpty()) 
        {
            System.out.println("📭 WeatherService: 출력할 데이터가 없습니다.");
            return;
        }
        
        for (WeatherDTO weather : weatherDataList) {
            System.out.println("📅 " + weather.getDate() + " - 평균기온: " + weather.getAverageTemperature() + "℃");
        }
    }

    /**
     * 날씨 데이터 수신 확인
     */
    @Override
    public void checkWeatherDataReceived() 
    {
        System.out.println("🔍 WeatherService: 날씨 데이터 수신 확인");
        List<WeatherDTO> weatherList = weatherRepository.findAll();
        System.out.println("✅ WeatherService: " + weatherList.size() + "개의 데이터 수신 완료");
    }
    
    @Override
    public Messenger saveWeatherWithStatus(WeatherDTO weatherDTO) {
        return weatherRepository.saveWithStatus(weatherDTO);
    }
    
    @Override
    public Messenger checkWeatherDataStatus() {
        System.out.println("📥 WeatherServiceIpml: WeatherController로부터 상태 확인 요청 수신");
        System.out.println("📤 WeatherServiceIpml: WeatherRepository로 상태 확인 요청 전송");
        Messenger result = weatherRepository.checkDataStatus();
        System.out.println("📨 WeatherServiceIpml: WeatherRepository에서 받은 상태: " + result.getMessage());
        System.out.println("📤 WeatherServiceIpml: WeatherController로 상태 전송");
        return result;
    }
    
    @Override
    public Messenger getAllWeatherDataWithStatus() {
        return weatherRepository.findAllWithStatus();
    }
    
    @Override
    public Messenger deleteWeatherWithStatus(String date) {
        return weatherRepository.deleteWithStatus(date);
    }
    
    @Override
    public Messenger updateWeatherWithStatus(WeatherDTO weatherDTO) {
        return weatherRepository.updateWithStatus(weatherDTO);
    }
    
    @Override
    public Messenger saveAllWeatherWithStatus(List<WeatherDTO> weatherDTOList) {
        System.out.println("📤 WeatherServiceIpml: WeatherController로부터 " + weatherDTOList.size() + "개 데이터 수신");
        System.out.println("📤 WeatherServiceIpml: WeatherRepository로 데이터 전송");
        Messenger result = weatherRepository.saveAllWithStatus(weatherDTOList);
        System.out.println("📨 WeatherServiceIpml: WeatherRepository에서 받은 결과: " + result.getMessage());
        System.out.println("📤 WeatherServiceIpml: WeatherController로 결과 전송");
        return result;
    }
}
