package br.dev.as.ccz.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CCZ Online - Fase 5 FIAP",
                description = "API for managing tasks",
                version = "1.0.0",
                contact = @Contact(
                        name = "A. Schneider",
                        email = "schn.alessandro@gmail.com",
                        url = "http://github.com/aschneider12"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Servidor de Desenvolvimento"
                )
        },
        tags = {
                @Tag(
                        name = "Autenticação",
                        description = "Endpoints para autenticação de usuários"
                ),
                @Tag(
                        name = "Usuários",
                        description = "Operações CRUD para gerenciamento de usuários"
                )
        }
)
public class SwaggerConfig {
}
