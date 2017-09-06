FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER Marten Sigwart <marten@okamaja.de>
VOLUME /tmp
ADD target/fakeload-server-0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENV ARGUMENTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar $ARGUMENTS" ]