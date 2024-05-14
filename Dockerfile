# Developement setup for springboot application
FROM --platform=linux/amd64 ubuntu:20.04

WORKDIR /app

# install sdkman
RUN apt-get update && apt-get install -y curl zip unzip git
RUN curl -s "https://get.sdkman.io" | bash
# install java 17 with sdkman
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install java 17.0.10-oracle"
# install maven and gradle with sdkman
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install maven && sdk install gradle"
# install springboot with sdkman
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install springboot"

COPY . .

# install nodejs
RUN curl -sL https://deb.nodesource.com/setup_20.x | bash -
RUN apt-get install -y nodejs

# install npm packages
# RUN cd /app/frontend && npm install


EXPOSE 8080

# CMD ["/bin/bash", "-c", "source /root/.sdkman/bin/sdkman-init.sh && mvn spring-boot:run"]
CMD ["mvn", "clean", "spring-boot:run"]