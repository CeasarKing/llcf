FROM ubuntu

ENV WORK_SPACE /home/lin/llcf

RUN pwd >> bar
RUN mkdir -f ${WORK_SPACE}
RUN ls

EXPOSE 8080
