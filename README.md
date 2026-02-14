# Hackaton - CCZ SUS

## O Projeto
Baseado nas limitações encontradas na área de controle de zoonoses, esse projeto consiste em uma ferramenta unificada para cidadãos e agentes municipais integrarem as comunicações e mapeamentos de dados relacionada a zoonoses da região de sua atuação.


## Vídeo de apresentação
[Pitch e funcionamento da aplicação](https://drive.google.com/drive/u/1/folders/1kkN0c2gEqYtE7vl_U11Z-Msuy5pexNGy)

## Tecnologias utilizadas

- Java 21  
- Spring Boot 3.4.4  
- Spring Security (JWT)  
- PostgreSQL  
- Flyway  
- Swagger (SpringDoc)  
- Docker & Docker Compose
- Expo
- React Native

  

## Como executar o projeto

Certifique-se de ter **Docker** e **Docker Compose** instalados.

Execute o comando abaixo na raiz do projeto:
Case esteja no powershell
```bash
$env:EXPO_PUBLIC_GOOGLE_MAPS_API_KEY="CHAVE DA API DO GOOGLE MAPS"
docker compose up --build 
```
Case esteja no Bash

```bash
EXPO_PUBLIC_GOOGLE_MAPS_API_KEY=CHAVE DA API DO GOOGLE MAPS docker compose up --build
```

## Documentação da API (Swagger)

Após subir o backend, acesse:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)

---

## Autores

- [Alessandro Schneider](https://github.com/aschneider12)
- [Eduardo Serafim](https://github.com/EduardoSerafim)

Desenvolvido para o Hackaton da Pós graduação em arquitetura e desenvolvimento Java - FIAP
