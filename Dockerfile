FROM maven:3.9.6-eclipse-temurin-21 AS test-runner

RUN apt-get update && apt-get install -y \
    wget \
    curl \
    gnupg \
    unzip \
    && curl -sS https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" | tee /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .

RUN chmod +x ./notify.sh

CMD ["bash", "-c", "mvn clean test allure:report; ./notify.sh $?"]
