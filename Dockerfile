FROM docker-hub-green.tools.huawei.com/openjdk:8-jre-alpine
LABEL MAINTAINER="DevCloud <devcloud@huawei.com>"

COPY ./target/demoapp.jar /app/app.jar
COPY hello-world.yml /app/config.yml
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
CMD ["server","/app/config.yml"]
