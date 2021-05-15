# Logub

Logub is an application to collect, explore and analyze application logs.
Proudly powered by Redis for log storage and RediSearch for the exploration and analysis part.

![Alt text](https://github.com/Logub/logub/blob/main/images/home-with-env-filter.png?raw=true "Home Logub")

![Alt text](https://github.com/Logub/logub/blob/main/images/log-details-popup.png?raw=true "Log details")

<br/>
<br/>

## Architecture

Logub Architecture Diagram :

![Alt text](https://github.com/Logub/logub/blob/main/images/architecture.png?raw=true "Architecture Logub")

## How it works?

Logub use [Fluentd](https://www.fluentd.org) to collect, format, and send logs to Redis. The Logub backend then allows to interact with the logs, to do a full-text search, and to index some fields defined by the user (with RediSearch).

### How data are stored?

Data are stored using the [Fluentd Redis Plugin](https://github.com/fluent-plugins-nursery/fluent-plugin-redis).
It stores each log with `HSET`. For example: `HSET level DEBUG message "Hello World" thread main`

### How data are queried?

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
  private Optional<String> service = Optional.empty();

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
  Optional<String> containerName= Optional.empty();

  @Builder.Default
  Optional<String> containerId= Optional.empty();

  @Builder.Default
  Optional<String> env= Optional.empty();

  @Builder.Default
  Optional<String> host= Optional.empty();

}


```

This is the object we use to manipulate logs and retrieve them from the database to allow the Logub UI to display the logs. To make complex queries on our logs we use Redis Search on the top of the Redis database. As we have added the possibility for the user to change the Redis search schema dynamically, we used the "List" data structure to keep track of which schema is indexed.

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

This is the object which allows us to create a plain text RediSearch query based on the user input. As you can see we create our own (small) QueryBuilders on the top of the JRedisSearch Libary. It's this object that will be sent by Logub UI to make a powerful search in your logs.
<br/>
<br/>

## How to run it locally? (DEMO)

In the demo, a DEMO App publishes logs in Logub. You will be able to interact with this DEMO app to generate your logs and then be able to request them in Logub.

### Prerequisites

- Docker - v20.10
- Docker Compose - v1.29

<br/>

### Launch Logub demo

Make sure that the given ports are free on your PC: `8080, 8081, 3000` & `6379`.

- Go to `/demo` folder (`cd ./demo`)
- Launch the docker-compose with the given command:

```
docker-compose up -d
```

Go to `localhost:3000` to explore logs. (wait 1 minute to see logs coming.) <br/>
On this page, you can view the logs, filter them via the sidebar on the right or search by filter or full-text query via the search bar at the top.
When you click on a log, the details are displayed and you have the possibility to index business properties. The business properties can be used as filters afterwards

Go to `localhost:3000/demo` to access the playground to add your custom logs. <br/>
The demo page allows you :

- To create fake users in the demo fake app and see them in logs.
- To publish your logs in the system.

If you return to the main page, you can try to search for the logs you have generated !

**WARNING: There is a latency of about 1 minute between the production of a log in a container and its display in Logub. This latency is due to the process of collecting, formatting, and ingesting the logs into the database.**
### How work the search bar ? 
You can do search by tag or full-text here are some example :

- `env:dev Ut ea vero voluptate*` will search all logs in the dev environnement with a message that start with  `Ut ea vero voluptate`
- `-env:prod Ut ea vero voluptate*` will search all logs in all environnement except prod with a message that start with `Ut ea vero voluptate`
- `originRequest:France originRequest:USA` will search all logs with a field originRequest with a value set at France or USA
- `"dog" "cat"` will search all logs with a that contains "dog" and "cat"
- `-"dog" "cat"` will search all logs with a that don't contain "dog" but contain "cat"

In order to have a good testing experience with the app we highly recommend you to create your own log with the playground, add business properties and play with it.

## How to integrate it into your project?

You can integrate Logub into your project. For now, Logub is only available in Docker environment.
You will need four Docker images :

- Logub fluentd image to collect and send logs to Redis - [Logub Fluentd Image](https://hub.docker.com/r/logub/logub-fluentd)
- Redis image with RediSearch module [Redis Mod Image](https://hub.docker.com/r/redislabs/redismod)
- Logub controller image to serve log exploring functionnalities - [Logub Contoller Image](https://hub.docker.com/r/logub/logub-controller)
- Logub UI to explore and query logs - [Logub UI Image](https://hub.docker.com/r/logub/logub-ui)

#### Logub log format

For now, Logub can handle one particular log format described here. In the future, this format will be extended and more customizable.

The Logub log format is :

```
{
 "level": "....",
 "thread": "....",
 "logger": "....",
 "message": "....."
}
```

Fields are not mandatory.

If you want to add your business properties, you need to add a nested JSON object with "mdc" as a key. For example :

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

<br/>

#### Publish logs in Logub

To explore logs in Logub, your containers need to use the Docker Fluentd logging driver.
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

## Redis in Logub

### Redis Search

Logub uses the functionality of RediSearch to process application logs. When logs are persisted in the Redis database, they are accompanied by 3 types of fields.

- SystemProperties are information that Docker & Fluentd gives us when sending the logs, like the environment, the container name, and many others.
- BasicProperties which are the basic information that a log have (eg: timestamp, level, service, loggerName, or the message)

These properties are automatically indexed in RedisSearch.

- Business properties are given by the user of Logub in a specific field. The user has to respect a Key - Value (Map) format.
  We use the dynamic index of Redis search to allow the user to index these "business properties" if he wants to do some research on it. <br/>
  _For example : A team uses correlationId in there logs to facilitate debug. If they insert the property in a JSON map into their logs then they will be able to query it in Logub._

```
{
  "timestamp": "2021-05-14 11:01:11.686",
  "level": "WARN",
  "thread": "scheduling-1",
  "mdc": {
    "app": "Toughjoyfax",
    "correlationId": "521f075f-36be-4f85-957e-d1c87ad71aa8",
    "originRequest": "Tonga",
    "origin": "LoremIpsum"
  },
  "logger": "com.loghub.loggenerator.service.LoggerService",
  "message": "Doloremque dolores ut minima sed."
}
```

Here we have an example of a log that describes how our tool works, when Fluentd flattens and persists in the Redis database. <br/>
The Logub API allows the user or company to index one or all fields of the mdc object.

In this project, the **_Tag Datatype_** is widely used. As we can see from our experiences we often search logs based on business properties when searching in logs (eg: a customer id).
Furthermore, we also use the **_TextField Datatype_** for the log messages, which allow the user to do a full-text search on this field.

Here are a simplified schema of the search process
![Alt text](https://github.com/Logub/logub/blob/main/images/Flow_of_search.png?raw=true "Flow of search")

### Redis

As we say before Redis is used to store our logs by Fluentd like this in the **_HashSet type_** of Redis.
![Alt text](https://github.com/Logub/logub/blob/main/images/log_in_redis.png?raw=true "Logs in Redis")
To keep track of the indexed field by the user we also add a "schema" object which uses the **_List type_** of Redis
![Alt text](https://github.com/Logub/logub/blob/main/images/schema_in_redis.png?raw=true "Schema in Redis")
