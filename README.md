# 🦜Parrot - BACKEND
<p align="center">
 <img  src="https://user-images.githubusercontent.com/80289718/208725397-62059674-482d-4a24-87ed-a13a0d36f88a.png" width="100px;" alt="Gabriel Ribeiro"/>
</p>

## 💻 Sobre o projeto
 Projeto final do Programa Trainee [SysMap](https://sysmap.com.br) de Excelência Full Stack | 3ª edição e trata-se de uma rede social feita em Java com o Framework Spring.

## 🧰 Tecnologias utilizadas

* Java
* Springboot
* Spring Web
* Spring Data
* Spring Security
* Lombok
* Bcrypt
* MongoDB
* Swagger API
* JWT (JSON Web Token) Authentication
* Docker
* Localstack
* Bucket AWS S3

<br>

## ⚙ Requisitos
 Para poder rodar este projeto, você deverá ter os seguintes programas instalados:
 - Docker
  
  <br>

## 👩‍💻Instalação

  * Clone este projeto
  * Entre na pasta raiz de <i>"Gabriel-Ribeiro-Backend"</i> e abra o arquivo <a href="https://github.com/bc-fullstack-03/Gabriel-Ribeiro-Backend/blob/main/docker-compose.yaml">docker-compose.yaml</a>
  * Após isso, insira no terminal o comando abaixo:

```
docker-compose up
```
 * Entre no terminal do container do localhost
 * Insira o comando para definir o usuário padrão 
 ```
aws configure --profile default
```
 ```
 AWS Access Key ID [None]: mykey
AWS Secret Access Key [None]: mykey
Default region name [None]: us-west-2
Default output format [None]: json
 ```

* Após definir o usuário, insira o código abaixo para criar o bucket S3.
```
 aws s3 mb s3://demo-bucket --endpoint-url http://localhost:4566
 ```
 
 ![terminal](https://user-images.githubusercontent.com/80289718/236905442-d81e9d9f-0035-48ce-816a-6e0506f0cdc1.jpg)

## ⚠️⚠️⚠️
  ```diff
@@ CASO VOCÊ PARE O CONTAINER, TERÁ QUE CRIAR O BUCKET NOVAMENTE ! @@
```
 🚏 Isso acontece porque estamos utilizando uma versão gratuita do [Localstack](https://localstack.cloud) </h3> 
  
* Após ter concluido as configurações iniciais, você está pronto para utilizar o sistema!

 <br>

## 🛣 Documentação SWAGGER
  Para acessar  a documentação SWAGGER e fazer os testes de requisições, use a seguinte rota :
###  ```GET /swagger-ui/index.html```
###  ```localhost:8080/swagger-ui/index.html```

![swagger](https://user-images.githubusercontent.com/80289718/236886929-2b8c53a6-291a-470e-addf-f2cd8a12befe.png)
### Obs: Não esqueça de fazer a autenticação no swagger antes de fazer as requisições!
![Screenshot 2023-05-09 110415](https://github.com/bc-fullstack-03/Gabriel-Ribeiro-Backend/assets/80289718/1edaff42-5d46-4e15-bee6-ff8dd479563f)

 ## :construction: Status 
 ### __**<u>Ambiente de Back-end da Aplicação**</u>__ - [STATUS: Finalizado✅]  
 
## 🦸 Autor

<a href="https://github.com/Gahbr">
 <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/80289718?v=4" width="100px;" alt="Gabriel Ribeiro"/>
 <br />
 <sub><b>Gabriel Ribeiro</b></sub></a> <a href="https://github.com/Gahbr" title="github"></a>
 <br />

[![Linkedin Badge](https://img.shields.io/badge/-Gabriel-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/gabriellribeiro1/)](https://www.linkedin.com/in/gabriellribeiro1/)
[![Yahoo!](https://img.shields.io/badge/Yahoo!-6001D2?style=flat-square&logo=Yahoo!&logoColor=white)](mailto:gabriell.ribeiro@yahoo.com)
[![GitHub](https://img.shields.io/badge/Gahbr-%23121011.svg?style=flat-square&logo=github&logoColor=white)](https://github.com/Gahbr)
