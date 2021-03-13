FROM itingluo/maven

ENV WORK_SPACE /home/lin/llcf/

RUN mkdir -p ${WORK_SPACE} && mvn -U clean package -Dmaven.test.skip=true && cp ./llcf-server/target/llcf-server-0.0.1-SNAPSHOT.jar ${WORK_SPACE}

EXPOSE 8080
