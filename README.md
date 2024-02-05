# Minesweeper REST App

## Description
This app is a server side of a minesweeper game from https://www.studio-tg.ru/

## Installation
First clone this repository to your local machine:
```
git clone
```
Next add **.env** file to the root folder of the project. It must contain environment variables listed below:
```
DB_URL=jdbc:postgresql://minesweeper-db/postgres
DB_USERNAME=<database username>
DB_PASSWORD=<password for database user>
DB_PORT=<database external port>
PG_VERSION=<PostgreSql version to use>

```
You must have docker and docker compose installed.

Finally, run: 
```bash
docker compose up -d
```
This command will build new image from the [Dockerfile](/Dockerfile) for the app and starts containers with app and database.

## Usage
To play get [here](https://minesweeper-test.studiotg.ru/) and change URL API to **[your url]/game**