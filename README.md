# Eco-Assistant

## Requirement 
You need to have .env file with the following environment variable :<br>
- MAIL_TOKEN: the mail token
- MAIL_ADDRESS: the mail address
- POSTGRES_HOST: the name of the postgres container(db)
- POSTGRES_PORT: the port of the postgres server(5432)
- POSTGRES_USER: the login for the postgres server
- POSTGRES_DB_NAME: the name of the database 
- POSTGRES_PASSWORD: the password for the postgres server

## Installation 
For run this project you need docker-compose.
Then you need to run this command. 

```shell
docker-compose up --build
```
