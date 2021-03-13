FROM ubuntu

ENV WORK_SPACE /home/lin/llcf
RUN mkdir -p ${WORK_SPACE}
RUN pwd >> text1 && ls >> text2

COPY llcf-server-0.0.1-SNAPSHOT.jar ${WORK_SPACE}/

EXPOSE 8080
