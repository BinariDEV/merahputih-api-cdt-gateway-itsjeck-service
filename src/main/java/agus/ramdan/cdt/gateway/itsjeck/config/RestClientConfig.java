package agus.ramdan.cdt.gateway.itsjeck.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {
    @Bean
    public RestClient restClient(RestClient.Builder builder,PaymentGatewayConfig paymentGatewayConfig) {
        return builder
                .baseUrl(paymentGatewayConfig.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, paymentGatewayConfig.getApiKey())
                .build();
    }
}
