# -----------------------------------------------------------
# 階段 1: Build 階段
# -----------------------------------------------------------
FROM maven:3.9.5-eclipse-temurin-21 AS builder

# 設定工作目錄
WORKDIR /app

# 複製 Root pom.xml
COPY pom.xml .

# 運行一個空構建來下載依賴 (加快後續構建速度)
RUN mvn dependency:go-offline

# 複製整個專案目錄 (包括所有子模組)
# 注意：這裡複製整個上下文 (Build Context)
COPY . .

# 執行最終構建，生成所有子模組的 JAR/WAR 檔案
# JAR 檔案會產生在 /app/base-rest/target/ 目錄下
RUN mvn clean package -DskipTests

# -----------------------------------------------------------
# 階段 2: Runtime 階段 (請確保您的 JAR 檔名和路徑正確)
# -----------------------------------------------------------
FROM eclipse-temurin:21-jre-jammy
ENV TZ=Asia/Taipei
EXPOSE 8080

# 假設您的 base-rest 輸出的 JAR 檔名為 base-rest-1.0.jar
ARG REST_JAR_NAME=base-rest-1.0.jar
# 最終 JAR 檔案在容器內的路徑
ARG JAR_FILE=/den-den-homework/base-rest/target/${REST_JAR_NAME}

COPY --from=builder ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]