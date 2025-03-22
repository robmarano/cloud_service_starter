#
# Dockerfile for my Zookeeper application
#
# docker build -t transcriptor:v1 .
ARG BASE_NAME=ubuntu
ARG BASE_IMAGE=24.04
FROM $BASE_NAME:$BASE_IMAGE
ENV APP="djava"

RUN /bin/rm -rf /opt

# TODO need to update
#RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys A122542AB04F24E3

RUN apt-get update && apt-get update -y

# TODO need to update
#RUN apt-get install gpg

# TODO need to update
#RUN wget -O - https://apt.corretto.aws/corretto.key | gpg --dearmor -o /usr/share/keyrings/corretto-keyring.gpg && echo "deb [signed-by=/usr/share/keyrings/corretto-keyring.gpg] https://apt.corretto.aws stable main" | tee /etc/apt/sources.list.d/corretto.list

#RUN echo "deb https://apt.corretto.aws stable main" | tee /etc/apt/sources.list.d/corretto.list

#RUN apt-get update; apt-get install -y java-21-amazon-corretto-jdk

RUN apt-get update; apt-get install -y openjdk-21-jdk

# TODO: remove vim, dnsutils, dos2unix for production
RUN apt-get install -y dnsutils dos2unix vim

# Copy the application into the container
COPY $APP-0.0.1-SNAPSHOT.jar /$APP.jar

# Copy the entrypoint script into the container
#COPY $APP-entrypoint.sh /$APP-entrypoint.sh
#RUN dos2unix /$APP-entrypoint.sh
#RUN chmod +x /$APP-entrypoint.sh
COPY djava-entrypoint.sh /djava-entrypoint.sh
RUN dos2unix /djava-entrypoint.sh
# Set the entrypoint script to be executable
RUN chmod +x /djava-entrypoint.sh
RUN mkdir -p logs

# Remove unnecessary packages to increase security and reduce the size of the image
#RUN apt-get remove -y --purge \
#    gpg \
#    wget \
#    apt-transport-https \
#    ca-certificates \
#    gnupg \
#    software-properties-common \
#    dnsutils \
#    dos2unix \
#    && apt-get autoremove -y

# port for spring boot app
EXPOSE 3000
# port for spring boot mgmt
EXPOSE 3001

# Set the entrypoint script to be the entrypoint for the container
#ENTRYPOINT ["/$APP-entrypoint.sh"]
ENTRYPOINT ["/djava-entrypoint.sh"]

# Set the default command to be the Zookeeper application
#CMD ["java", "-jar", "/zkApp.jar"]
#CMD ["/bin/bash"]
#CMD ["/bin/bash","-c","/transcriptor-entrypoint.sh"]