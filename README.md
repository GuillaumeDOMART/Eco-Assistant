# Eco-Assistant

## Requirement 
You need to have .env file with the following environment variable :

```
MAIL_TOKEN: the mail token
MAIL_ADDRESS: the mail address
POSTGRES_HOST: the name of the postgres container(db)
POSTGRES_PORT: the port of the postgres server(5432)
POSTGRES_USER: the login for the postgres server
POSTGRES_DB_NAME: the name of the database 
POSTGRES_PASSWORD: the password for the postgres server
SSL_CERT_PATH= the path of the ssl certificat
DOMAIN= the domain
VERSION= the version number
SECRET_KEY= The secret key can be generated with the flowing command "openssl rand -base64 32"
```


## Local Installation 
To run this project you need docker-compose.
Then you need to run this command. 

```shell
docker-compose up --build
```

## Deployment on server

To deploy the application to the server, deploy the source of the project
into your server (SCP, FTP...). You may use this shell script to deploy it:

```shell
cd EcoAssistantDirectory
docker compose build
screen -XS EcoAssistantDeamon kill
screen -S EcoAssistantDeamon -d -m docker compose up 
```

### Front
Note that to be allowed to run a reactJS build into a server, you'll have the need to have this 
line into the Dockerfile of the front to be able to build
the front and to then server the build created

```Dockerfile
CMD ["/bin/bash", "-c", "npm run build;npm install -g serve;serve -s build"]
```

### Nginx

Do not forget to use the nginx-prod.conf that redirect the 
http to the https ports and thus disable the http use.


Also to add into the docker-compose.yml the use of the 443 port
(https) and the volume to the SSL certificate located onto
the server to the docker container of the Nginx service

