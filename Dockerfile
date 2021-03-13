FROM ubuntu

ENV WORK_SPACE /home/lin/llcf

RUN mkdir -f ${WORK_SPACE}
COPY llcf-server-0.0.1-SNAPSHOT.jar ${WORK_SPACE}/llcf-server-0.0.1-SNAPSHOT.jar
RUN ls

EXPOSE 8080
