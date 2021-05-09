# fluentd/Dockerfile

FROM fluent/fluentd:v1.12-debian
USER root
RUN ["gem", "install", "fluent-plugin-redis"]
RUN ["gem", "install", "fluent-plugin-rename-key"]
RUN ["gem", "install", "fluent-plugin-flatten-hash"]
USER fluent
