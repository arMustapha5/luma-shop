# Stage 1: Build stage - compile and prepare application
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy dependencies first for better caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application (compile only, no tests)
RUN mvn clean package -DskipTests

# Stage 2: Test and Runtime stage
FROM maven:3.9.6-eclipse-temurin-21 AS test-runner

# Install Chrome for testing
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

# Copy Maven configuration and dependencies from builder
COPY --from=builder /root/.m2 /root/.m2
COPY pom.xml .

# Copy compiled classes and resources from builder
COPY --from=builder /app/target ./target

# Copy source code for tests
COPY src ./src
COPY notify.sh .
RUN chmod +x ./notify.sh

# Set command to run tests and notify
CMD ["bash", "-c", "mvn test allure:report && ./notify.sh $?"]