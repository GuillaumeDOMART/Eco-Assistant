# Eco-Assistant

## Requirement 
You need to have .env file with the following environment variable :<br>
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
SECRET_KEY= The secret key can generate with the flowing command "openssl rand -base64 32"
```

## Installation 
For run this project you need docker-compose.
Then you need to run this command. 

```shell
docker-compose up --build
```
