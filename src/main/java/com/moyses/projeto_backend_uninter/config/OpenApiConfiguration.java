package com.moyses.projeto_backend_uninter.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Projeto Backend Uninter",
                version = "v1.1",
                contact = @Contact(
                        name = "Moyses Zerbieti",
                        email = "zerbietimoyses@gmail.com",
                        url = "https://github.com/Moyses-Zerbieti/projeto-Uninter"
                )
        )
)
public class OpenApiConfiguration {

}
