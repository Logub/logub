# Logub

Logub is an application to collect, explore and analyze application logs.
Proudly powered by Redis for log storage and RediSearch for the exploration and analysis part.

![Alt text](https://raw.githubusercontent.com/Logub/logub/main/images/home-advanced-filter.png "Home Logub")

![Alt text](https://github.com/Logub/logub/blob/main/images/log-details-popup.png?raw=true "Home Logub")

<br/>
<br/>

## Architecture

Logub Architecture Diagram :

![Alt text](https://github.com/Logub/logub/blob/main/images/architecture.png?raw=true "Architecture Logub")
## How it works ?

Logub use [Fluentd](https://www.fluentd.org) to collect, format and send logs to Redis. The Logub backend then allows to interact with the logs, to do full text search and to index some fields defined by the user (with RediSearch).

### How data are stored ?

Data are stored using the [Fluentd Redis Plugin](https://github.com/fluent-plugins-nursery/fluent-plugin-redis).
It stores each log with `HSET` so for example : `HSET level DEBUB message Hello World thread main`

### How data are queried ?

```JAVA
public class LogubLog {
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  @Builder.Default
  private String index = "principal";

  @NonNull
  private SystemProperties systemProperties;

  @Builder.Default
  private Map<String, Object> businessProperties = Collections.emptyMap();

  @Builder.Default
  private Optional<String> message = Optional.empty();

  @Builder.Default
  private Instant timestamp = Instant.now();

  @Builder.Default
  private Optional<String>  service = Optional.empty();

  @Builder.Default
  private Optional<String> logger = Optional.empty();

  @Builder.Default
  private Optional<String> thread = Optional.empty();
  @Builder.Default
  private Optional<String> source = Optional.empty();
  @Builder.Default
  private LogLevel level = UNKNOWN;
}

public class SystemProperties {
 @Builder.Default
 Optional<String> imageName = Optional.empty();

 @Builder.Default
 Optional<String>  containerName= Optional.empty();
 @Builder.Default
 Optional<String>  containerId= Optional.empty();

 @Builder.Default
 Optional<String>  env= Optional.empty();

 @Builder.Default
 Optional<String>  host= Optional.empty();

}


```

This is the object we use in order to manipulate logs and retrieve it from the database to allow the Logub UI to display the logs. In order to make complexe query on our logs we use Redis Search on the top of the redis database. As we have added the possibility for the user to change the redis search schema dynamically, we used the "List" data structure in order to keep track of which schema is indexed.

```JAVA
public class LogSearch {

  @Builder.Default
  private List<LogubFieldSearch> texts = emptyList();
  @Builder.Default
  private List<LogubFieldSearch> systemProperties = emptyList();
  @Builder.Default
  private List<LogubFieldSearch> businessProperties = emptyList();

  @Builder.Default
  private List<LogubFieldSearch> basicProperties = emptyList();

  @Builder.Default
  private List<LogubFieldSearch> levels = Collections.emptyList();
  @Builder.Default
  private int limit = 25;
  @Builder.Default
  private int offset = 0;
  @Builder.Default
  private Optional<LogubSort> sort = Optional.empty();
  @Builder.Default
  private Instant beginAt = Instant.now().minus(15, ChronoUnit.MINUTES);
  @Builder.Default
  private Instant endAt = Instant.now();


  @SneakyThrows
  public QueryBuilder toQuery() {
    var query = new QueryBuilder();
    var businessPrefix = "businessProperties.";
    var systemPropertiesPrefix = "systemProperties.";
    for (LogubFieldSearch properties : businessProperties) {
      query.append(QueryBuilders.tag(businessPrefix + properties.getName(), properties.getValues(),
          properties.isNegation()));
    }
    for (LogubFieldSearch properties : systemProperties) {
      query.append(QueryBuilders
          .tag(systemPropertiesPrefix + properties.getName(), properties.getValues(),
              properties.isNegation()));
    }
    for (LogubFieldSearch properties : basicProperties) {
      query.append(QueryBuilders
          .tag(properties.getName(), properties.getValues(),
              properties.isNegation()));
    }
    if (!levels.isEmpty()) {
      for (LogubFieldSearch level : levels) {
        var onError = !level.getValues().stream().allMatch(v -> Arrays.stream(LogLevel.values())
            .anyMatch(enumLevel -> enumLevel.name().equalsIgnoreCase(v)));
        if(onError){
          log.error("bad payload for levels {}", level);
          throw new IllegalArgumentException("bad payload for level");
        }
        query.append(QueryBuilders.tag("level",level.getValues(), level.isNegation()));
      }
    }
    for (LogubFieldSearch text : texts) {
      if(!text.getType().equals(LogubFieldType.FullText)){
        log.warn("type {} not handle for text search", text.getType());
      }
      for (String value : text.getValues()) {
        query.append(QueryBuilders.text("message", value, text.isNegation()));
      }
    }

    return query;
  }


}
```

This is the object which allow us to create a plain text redis search query based on the user input. As you can see we create our own (small) QueryBuilders on the top of the JRedisSearch Libary. It's this object that will be send by Logub UI in order to make powerfull search in your logs.
<br/>
<br/>

## How to run it in local ? (DEMO)

In the demo, a DEMO App publish logs in Logub. You will be able to interact with this DEMO app in order to generate your own logs to then be able to request them in Logub.

### Prerequisites

- Docker - v20.10
- Docker Compose - v1.29

### Launch Logub demo

Make sure that the given ports are free on your PC : `8080, 8081, 3000`

- Go to `/demo` folder (`cd ./demo`)
- Launch the docker-compose with the given command:

```
docker-compose up -d
```

Go to `localhost:3000` to explore logs. (wait 1 minute to see logs coming.) <br/>

Go to `localhost:3000/demo` to access to the playground to add your custom logs. <br/>
The demo allows you :

- To create fake users in the demo fake app and see them in logs.
- To publish your own logs in the system.

WARNING : There is a latency of about 1 minute between the production of a log in a container and its display in Logub. This latency is due to the process of collecting, formatting and ingesting the logs into the database.

## How to integrate it to your project ?

You can integrate Logub to your project. For now, Logub is only available in Docker environment.
You will need four Docker images :

- Logub fluentd image to collect and send logs to Redis - [Logub Fluentd Image](https://hub.docker.com/r/logub/logub-fluentd)
- Redis image with RediSearch module [Redis Mod Image](https://hub.docker.com/r/redislabs/redismod)
- Logub controller image to serve log exploring functionnalities - [Logub Contoller Image](https://hub.docker.com/r/logub/logub-controller)
- Logub UI to explore and query logs - [Logub UI Image](https://hub.docker.com/r/logub/logub-ui)

Logub uses docker fluentd logging driver to ingest log.

#### Logub log format

For now, Logub can handle one particular log format described here. In the future, this format will be extended and more customizable.

The logub log format is :

```
{
  "level": "....",
  "thread": "....",
  "logger": "....",
  "message": "....."
}
```

Fields are not mandatory.

If you want to add your own business properties, you need to add a nested json object with "mdc" in key. For example :

```
{
  "level": "....",
  "thread": "....",
  "logger": "....",
  "message": ".....",
  "mdc":{
    "myproperties": "....",
    "anotherOne":".....",
  }
}
```

#### Publish logs in Logub

In order to explore logs in Logub, your containers need to use the Docker Fluentd logging driver.
Configuration example for custom integration :

```YAML
  # Your container(s)
  MY-CUSTOM-APP:
    image: "MY-CUSTOM-APP-IMAGE"
    logging:
      driver: "fluentd"
      options:
        fluentd-address: localhost:24224
        tag: MY-CUSTOM-TAG
  ## Logub Fluentd + Redis conf
  logub-controller:
    image: "logub/logub-controller:0.1"
    ports:
      - "8080:8080"
    depends_on:
      - redis
    links:
      - "fluentd"
      - "redis"
  logub-ui:
    image: "logub/logub-ui:0.1"
    ports:
      - "3000:3000"
    depends_on:
      - redis
  fluentd:
    image: "logub/logub-fluentd:0.1"
    links:
      - "redis"
    ports:
      - "24224:24224"
      - "24224:24224/udp"
  redis:
    image: "redislabs/redismod"
    command: ["/usr/local/etc/redis/redis.conf","--bind","redis","--port", "6379"]
    volumes:
      - ./redis/data:/data
      - ./redis/redis.conf:/usr/local/etc/redis/redis.conf
    ports:
      - "6379:6379"
```

<br/>
<br/>

## About Redis in Logub ?

### Redis Search

Logub uses the functionality of Redisearch to process application logs. When logs are persisted in the Redis database, they are accompanied by 3 types of fields.

- SystemProperties are the information that fluentd gives us when sending the logs, like the environment, the container name and many others.
- BasicProperties which are the basic information that a log have (eg: timestamp, level, service, loggerName or the message)

These properties are automatically indexed in RedisSearch.

- The Business properties, which are given by the user of logub in a specific field we ask the user to give which respect the Key - Value (Map) format.
  We use the dynamic index of redis search in order to allow the user to index these "custom properties" if he wants to do some research on it.

```
{"timestamp":"2021-05-14 11:01:11.686","level":"WARN","thread":"scheduling-1",
"mdc":{"app":"Toughjoyfax","correlationId":"521f075f-36be-4f85-957e-d1c87ad71aa8","originRequest":"Tonga","origin":"LoremIpsum"},
"logger":"com.loghub.loggenerator.service.LoggerService","message":"Doloremque dolores ut minima sed."}
```

Here we have an example of log which describe how our tools works, when fluentd flattent and persist it in the redis database,
the service we called "log-controller" will retrieve these data in the `POST logs/search`. At the top of the library 'JRedisSearch' we build our QueryBuilder in order to create RedisSearch query based on our model.
This makes it easier to search the logs, while using the power of Redis Search.
The Logub API allow user or company to index one or all field of the mdc object.

In this project the **_Tag Datatype_** are widely used. As we can see from our experiences we often search logs based on business properties when search in logs (eg: a customer id).
Furthermore, we also use the **_TextField Datatype_** for the log messages, that allow the user to do full text search on this field.

Here are a simplified schema of the search process
![Alt text](https://github.com/Logub/logub/blob/main/images/Flow_of_search.png?raw=true "Flow of search")

<<<<<<< HEAD
### Redis

As we say before redis in used in order to store our logs by fluentd like this in the **_Hash type_** of Redis.
![Alt text](./images/log_in_redis.png "Logs in redis")
In order to keep track of the indexed field by the user we also add a "schema" object which use the **_List type_** of Redis
![Alt text](./images/schema_in_redis.png "Schema in redis")
=======
### Redis 
As we say before redis in used in order to store our logs by fluentd like this in the ***Hash type*** of Redis.
![Alt text](https://github.com/Logub/logub/blob/main/images/log_in_redis.png?raw=true "Logs in redis")
In order to keep track of the indexed field by the user we also add a "schema" object which use the ***List type*** of Redis
![Alt text](https://github.com/Logub/logub/blob/main/images/schema_in_redis.png?raw=true "Schema in redis")
>>>>>>> 71cdc7d4a30c7851eb8db87c0402bc7e8544aaa1
