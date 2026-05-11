# Circuitos Energy

Aplicação web para gestão e apresentação de serviços da Circuitos Energy, com área pública, carrinho, checkout, encomendas, agendamentos, contacto, ferramentas de cálculo, orçamento de painéis solares e área de administração.

## Tecnologias usadas

- Java 21
- Spring Boot
- Maven
- Thymeleaf
- H2

## Funcionalidades principais

- Página inicial
- Serviços
- Detalhe de serviço
- Carrinho
- Checkout
- Encomendas
- Área admin
- Agendamentos
- Contacto
- Ferramentas de cálculo
- Orçamento de painéis solares
- Upload seguro de imagens
- Autenticação e autorização
- Proteção CSRF

## Como correr a aplicação

Entrar na pasta do projeto:

```powershell
cd circuitos-energy
```

Compilar:

```powershell
.\mvnw.cmd compile
```

Arrancar a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

Depois de iniciar, aceder em:

```text
http://localhost:8081
```

## Perfis e configurações importantes

Por defeito:

- H2 Console desativado.
- Criação automática de utilizadores padrão desativada.

Profile `dev`:

- `app.seed-default-users=true`
- H2 Console configurado apenas para desenvolvimento, mesmo que atualmente não seja necessário para uso normal da aplicação.

Para arrancar com o profile `dev`:

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

Também é possível ativar a criação dos utilizadores padrão numa base de dados nova com o argumento:

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--app.seed-default-users=true"
```

## Utilizadores de teste

Se a base de dados já tiver os utilizadores de teste:

- Admin: `Rafael` / `1234`
- Cliente: `cliente` / `1234`

Se for uma base de dados nova, usar o profile `dev` ou arrancar a aplicação com:

```text
--app.seed-default-users=true
```

## Base de dados

A aplicação usa uma base de dados H2 persistente em:

```text
./data/circuitos-energy
```

Não apagar a pasta `data/` se quiser manter os dados existentes.

## Uploads

As imagens enviadas pela área admin são guardadas em:

```text
uploads/
```

Formatos aceites:

- `jpg`
- `jpeg`
- `png`
- `webp`

Limite por ficheiro:

```text
2MB
```

## Validação final

Foram testados os principais fluxos e proteções da aplicação:

- Login/logout
- Carrinho
- Checkout
- Encomendas
- Admin
- Uploads
- CSRF
- Ferramentas
- Agendamentos
- Permissões

## Notas de entrega

- Não incluir a pasta `target/` na entrega, se não for pedido.
- Não apagar `data/` nem `uploads/` sem backup.
