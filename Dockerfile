FROM ubuntu

ENV WORK_SPACE /home/lin/llcf
RUN mkdir -p ${WORK_SPACE}
RUN pwd >> text1 && ls >> text2

EXPOSE 8080
