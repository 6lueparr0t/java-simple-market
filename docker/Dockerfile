FROM openjdk:11
# Adding build tools to make yarn install work on Apple silicon / arm64 machines
RUN apk add --no-cache python2 g++ make
WORKDIR /app
COPY package.json yarn.lock ./
RUN yarn install --production
COPY project/. .
CMD ["java", "-jar", "-Dspring.profiles.active=local kakaopaycoding-server-1.0-SNAPSHOT*.jar"]