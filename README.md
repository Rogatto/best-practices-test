# best-pratices-forms-test:
As 8 melhores práticas e formas de simplificar e estruturar todos seus Testes Automatizados


# Execução do Selenium Grid:

**Executar arquivo docker compose em /docker/selenium-grid**

```sh
$ docker-compose up -d
```

**Dashboard:**
http://localhost:4444/ui/index.html#/

# Apontar nossos testes para o seguinte endpoint para executar localmente:

http://localhost:4444/wd/hub <br/>

# Atualizar arquivo de propriedades

**Dentro do diretório properties alterar o arquivo project.properties com suas credenciais do Trello e Mockaroo**

<b> 1.api_key_trello </b><br/>
<b> 2.token_trello </b><br/>
<b> 3.api_key_mockaroo </b><br/>


## Estrutura do projeto

O projeto foi estruturado da seguinte maneira:

```
src
    \main
        \java
            # Pasta onde contém todos os page objects e métodos relacionados
            \GoogleMenuPages.java
    \test
        \java
            # Exemplo de como podemos utilizar apis de serviços terceiros
            \apis
            # Exemplo de dados sendo consumidos em nossos testes
            \datatesting
            # Estruturar diferentes tipos de testes
            \diferentesniveis
            # Paralelismo exemplos
            \paralelismo
            # Paralelismo exemplos
            \reutilizacao
```