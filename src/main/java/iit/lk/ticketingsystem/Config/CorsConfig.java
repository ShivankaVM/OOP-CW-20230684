package iit.lk.ticketingsystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // Allow all origins
        config.addAllowedMethod("*");        // Allow all HTTP methods (GET, POST, etc.)
        config.addAllowedHeader("*");        // Allow all headers
        config.setAllowCredentials(true);    // Allow credentials if needed
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
