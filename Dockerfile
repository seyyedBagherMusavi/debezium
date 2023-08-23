FROM mongo:5.0.13
#
COPY mongo-shell.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/mongo-shell.sh

# Starting with MongoDB 4.4 the authentication enabled MongoDB requires a key
# for intra-replica set communication
RUN openssl rand -base64 756 > /etc/mongodb.keyfile &&\
    chown mongodb /etc/mongodb.keyfile &&\
    chmod 400 /etc/mongodb.keyfile

CMD ["mongod", "--replSet", "rs0", "--auth", "--keyFile", "/etc/mongodb.keyfile"]
