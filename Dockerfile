# syntax = docker/dockerfile:1.0-experimental 

# Developement setup for springboot application
FROM --platform=linux/amd64 ubuntu:20.04 AS dev

WORKDIR /app

# install sdkman
RUN apt-get update && apt-get install -y curl zip unzip git
RUN curl -s "https://get.sdkman.io" | bash
# install java 17 with sdkman
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install java 17.0.10-oracle"
# install maven and gradle with sdkman
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install maven 3.9.6"
# RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install gradle"
# install springboot with sdkman
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install springboot"

# update path
ENV PATH="/root/.sdkman/candidates/java/current/bin:/root/.sdkman/candidates/maven/current/bin:/root/.sdkman/candidates/springboot/current/bin:${PATH}"

COPY . .

# install nodejs
# RUN curl -sL https://deb.nodesource.com/setup_20.x | bash -
# RUN apt-get install -y nodejs

# install npm packages
# RUN cd /app/frontend && npm install


RUN --mount=type=secret,id=mysql_ca_cert cat /run/secrets/mysql_ca_cert > /usr/local/share/ca-certificates/ca-certificate.crt
RUN chmod 644 /usr/local/share/ca-certificates/ca-certificate.crt && \
    update-ca-certificates

EXPOSE 8080

# build the application
RUN mvn clean install -DskipTests
RUN mvn clean package

# CMD ["/bin/bash", "-c", "source /root/.sdkman/bin/sdkman-init.sh && mvn spring-boot:run"]
CMD ["mvn", "clean", "spring-boot:run"]

# Production setup for springboot application
FROM --platform=linux/amd64 openjdk:18-jre-slim AS prod

WORKDIR /app

COPY --from=dev /app/target/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
