FROM ubuntu

ENV WORK_SPACE /home/lin/llcf
RUN mkdir -p ${WORK_SPACE}
COPY /home/runner/work/llcf/llcf/llcf-server-0.0.1-SNAPSHOT.jar 

EXPOSE 8080
