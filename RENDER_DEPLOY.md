# Deploy no Render

## Configuracao

O projeto esta preparado para usar dois perfis:

- `dev`: H2 local em ficheiro, usado por defeito no desenvolvimento.
- `prod`: PostgreSQL, usado no Render atraves de variaveis de ambiente.

## Variaveis de ambiente no Render

Criar um Web Service Docker e configurar:

```text
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://HOST:PORT/DATABASE?sslmode=require
SPRING_DATASOURCE_USERNAME=UTILIZADOR
SPRING_DATASOURCE_PASSWORD=PASSWORD
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

O valor de `SPRING_DATASOURCE_URL` deve ser montado a partir dos dados da base PostgreSQL do Render. Nao usar o URL `postgres://...` diretamente; para Java/Spring deve ficar no formato `jdbc:postgresql://...`.

## Docker

Build local:

```bash
docker build -t circuitos-energy .
```

Run local com perfil de producao:

```bash
docker run --rm -p 8080:8080 ^
  -e SPRING_PROFILES_ACTIVE=prod ^
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://HOST:PORT/DATABASE?sslmode=require ^
  -e SPRING_DATASOURCE_USERNAME=UTILIZADOR ^
  -e SPRING_DATASOURCE_PASSWORD=PASSWORD ^
  circuitos-energy
```

## Render Web Service

Opcao recomendada:

1. Criar uma base PostgreSQL no Render.
2. Criar um Web Service ligado ao repositorio.
3. Selecionar ambiente Docker.
4. Confirmar `Dockerfile Path` como `./Dockerfile`.
5. Adicionar as variaveis de ambiente acima.
6. Fazer deploy.

O Render injeta a porta no ambiente atraves de `PORT`. A aplicacao usa `server.port=${PORT:8080}`, por isso fica compativel com Web Service.

## Ambiente local

Para desenvolvimento local:

```bash
.\mvnw.cmd spring-boot:run
```

Por defeito, a aplicacao usa o perfil `dev`, H2 local e porta `8081`.

Para compilar:

```bash
.\mvnw.cmd compile
```
