FROM ubuntu

ENV WORK_SPACE /home/lin/llcf
RUN mkdir -p ${WORK_SPACE}
RUN mvn -U clean package -Dmaven.test.skip=true 

COPY llcf-server-0.0.1-SNAPSHOT.jar ${WORK_SPACE}/

RUN cd ${WORK_SPACE}

CMD ['java','-jar', 'llcf-server-0.0.1-SNAPSHOT.jar']

EXPOSE 8080
