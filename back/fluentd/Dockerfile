# fluentd/Dockerfile

FROM fluent/fluentd:v1.11.5-debian-1.0
USER root
RUN ["gem", "install", "fluent-plugin-redis"]
RUN ["gem", "install", "fluent-plugin-rename-key"]
USER fluent
