# Logub

Logub is an application to collect, explore and analyze application logs.
Proudly powered by Redis for log storage and RediSearch for the exploration and analysis part.

<p align="center">
<a href="https://github.com/Logub/logub/blob/main/images/home-advanced-filter.png?raw=true"><img src="https://github.com/Logub/logub/blob/main/images/home-advanced-filter.png?raw=true" width="90%" height="auto"></a>
</p>
<p align="center">
<a href="https://github.com/Logub/logub/blob/main/images/log-details-popup.png?raw=true"><img src="https://github.com/Logub/logub/blob/main/images/log-details-popup.png?raw=true" width="90%" height="auto"></a>
</p>

<br/>
<br/>

## Architecture

Logub Architecture Diagram :

<p align="center">
<a href="https://github.com/Logub/logub/blob/main/images/architecture.png?raw=true"><img src="https://github.com/Logub/logub/blob/main/images/architecture.png?raw=true" width="90%" height="auto"></a>
</p>

<br/>
<br/>

## How it works ?

Logub use [Fluentd](https://www.fluentd.org) to collect, format and send logs to Redis. The Logub backend then allows to interact with the logs, to do full text search and to index some fields defined by the user (with RediSearch).

### How data are stored ?

Map ?

### How data are queried ?

RediSearch, indexation...

<br/>
<br/>

## How to run it locally ? (DEMO)

### Prerequisites

- Docker - v20.10
- Docker Compose - v1.29
- NodeJS 12+ and NPM 6+

### Launch Logub server

- Go to `/back` folder (`cd ./back`)  
- Launch the docker-compose with the given command:

```
docker-compose up -d
```

### Launch Logub UI

- Go to `/frontend` folder (`cd ./frontend`)
- Start the UI with the following command:

```
npm start
```

## How to integrate it to your project ?

You can integrate Logub to your project. For now, Logub is only available in Docker environment.
You will need three Docker images :

- Logub fluentd image to collect and send logs to Redis (LINK)
- Redis image with RediSearch module (LINK)
- Logub backend image to serve log exploring functionnalities

#### Logub log format

For now, Logub can handle one particular log format described here. In the future, this format will be extended and more customizable.

Format : JSON
PUT EXAMPLE + ENV VARIABLES FOR BUSINESS PROPS
