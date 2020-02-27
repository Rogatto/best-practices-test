# best-pratices-forms-test:
As 8 melhores práticas e formas de simplificar e estruturar todos seus Testes Automatizados


# Temos somente a dependência de instalação do Docker e Zalenium

docker pull elgalu/selenium <br/>
docker pull dosel/zalenium
  

# Execução do Zalenium:

**Executar container local para apontar nossos testes**

 docker run --rm -ti --name zalenium -p 4444:4444 \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v /tmp/videos:/home/seluser/videos \
    --privileged dosel/zalenium start <br/>
   
**Live:**
http://localhost:4444/grid/admin/live

**Dashboard:**
http://localhost:4444/dashboard/

# Apontar nossos testes para o seguinte endpoint para executar localmente:

http://localhost:4444/wd/hub <br/>

<b> Exemplo de como fazer o apontamento do Zalenium em Java: </b><br/>

DesiredCapabilities capabilities = new DesiredCapabilities(); <br/>
capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME); <br/>
capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX); <br/>
URL remoteWebDriverUrl = new URL("http://localhost:4444/wd/hub"); <br/>
driver = new RemoteWebDriver(remoteWebDriverUrl, capabilities); <br/>


# Atualizar arquivo de propriedades

**Dentro do diretório properties alterar o arquivo project.properties com suas credenciais do Trello e Mockaroo**

<b> 1.api_key_trello </b><br/>
<b> 2.token_trello </b><br/>
<b> 3.api_key_mockaroo </b><br/>