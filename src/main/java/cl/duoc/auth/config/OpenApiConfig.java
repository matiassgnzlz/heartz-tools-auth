package cl.duoc.auth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
    title = "Heartz-Tools Auth API",
    version = "1.0",
    description = "Servicio de autenticación, registro y emisión de tokens JWT"
))
public class OpenApiConfig {}