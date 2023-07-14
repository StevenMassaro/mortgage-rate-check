# Based on https://github.com/yukinying/chrome-headless-browser-docker/blob/master/chrome-stable/Dockerfile
FROM debian:stable-slim

ARG DEBIAN_FRONTEND=noninteractive

COPY target/cuofco-mortgage-checker-1.0-SNAPSHOT-jar-with-dependencies.jar cuofco.jar
COPY run.sh run.sh

RUN apt-get update -qqy \
  && apt-get -qqy install \
       chromium \
       dumb-init gnupg wget ca-certificates apt-transport-https curl dos2unix \
       ttf-wqy-zenhei \
       default-jre \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
    && dos2unix run.sh \
    && apt-get -qqy purge dos2unix

# for backward compatibility, make both path be symlinked to the binary.
RUN ln -s /usr/bin/chromium /usr/bin/google-chrome-unstable
RUN ln -s /usr/bin/chromium /usr/bin/google-chrome

RUN useradd headless --shell /bin/bash --create-home \
  && usermod -a -G sudo headless \
  && echo 'ALL ALL = (ALL) NOPASSWD: ALL' >> /etc/sudoers \
  && echo 'headless:nopassword' | chpasswd

RUN mkdir /data && chown -R headless:headless /data

USER headless

COPY target/cuofco-mortgage-checker-1.0-SNAPSHOT-jar-with-dependencies.jar cuofco.jar
CMD ["./run.sh"]
