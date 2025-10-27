package site.hohyun.api.weather.service;
import site.hohyun.api.weather.domain.WeatherDTO;
import site.hohyun.api.common.domain.Messenger;

import java.util.List;

public interface WeatherService
{
    void saveWeather(WeatherDTO weatherDTO);
    void saveAll(List<WeatherDTO> weatherDTOList);
    List<WeatherDTO> getAllWeatherData();
    public int getWeatherDataCount();
    public void printWeatherDataStatus();
    public void printWeatherDataList(List<WeatherDTO> weatherDataList);
    public void checkWeatherDataReceived();
    
    // Messenger 반환 메서드들
    Messenger saveWeatherWithStatus(WeatherDTO weatherDTO);
    Messenger checkWeatherDataStatus();
    Messenger getAllWeatherDataWithStatus();
    Messenger deleteWeatherWithStatus(String date);
    Messenger updateWeatherWithStatus(WeatherDTO weatherDTO);
    Messenger saveAllWeatherWithStatus(List<WeatherDTO> weatherDTOList);
}
