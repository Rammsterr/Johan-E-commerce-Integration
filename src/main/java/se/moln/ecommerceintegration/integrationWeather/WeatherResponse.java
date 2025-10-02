package se.moln.ecommerceintegration.integrationWeather;

// Filtrera enbart stad, väder och beskrivning
public record WeatherResponse(
        String city,
        double temperature,
        String description
) {}
