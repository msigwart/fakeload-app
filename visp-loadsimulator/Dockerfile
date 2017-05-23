FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER Marten Sigwart <e1638152@student.tuwien.ac.at>
VOLUME /tmp
ADD target/loadsimulator-0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]