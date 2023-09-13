package py.com.jaha.api.users.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import py.com.jaha.api.users.domain.models.commons.OpenApiDTO;

@Configuration
public class OpenApiConfig {
	
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "springdoc")
	public OpenApiDTO springDoc() {
		return new OpenApiDTO();
	}

    @Bean
    public OpenAPI customOpenAPI(OpenApiDTO springDoc) {
        return new OpenAPI()
                .info(new Info()
                        .title(springDoc.getTitle())
                        .description(springDoc.getDescription())
                        .version(springDoc.getVersion()));
    }
}
