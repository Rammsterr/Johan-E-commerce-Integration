package se.moln.ecommerceintegration.integrationWeather;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public WeatherService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.openweathermap.org").build();
    }

    public WeatherResponse getWeatherForCity(String city) {
        String url = "/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric&lang=sv";

        try {
            JsonNode node = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

                    double temp = node.get("main").get("temp").asDouble();
            String description = node.get("weather").get(0).get("description").asText();

            return new WeatherResponse(city, temp, description);

        } catch (Exception e) {
            e.printStackTrace();
            return new WeatherResponse(city, 0, "Fel vid anrop till OpenWeather");
        }
    }
}

