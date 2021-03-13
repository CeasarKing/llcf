FROM ubuntu

ENV WORK_SPACE /home/lin/llcf
RUN mkdir -p ${WORK_SPACE}
RUN pwd >> text1 && ls >> text2

COPY llcf-server-0.0.1-SNAPSHOT.jar ${WORK_SPACE}/

RUN cd ${WORK_SPACE}

CMD ['java','-jar', 'llcf-server-0.0.1-SNAPSHOT.jar']

EXPOSE 8080
