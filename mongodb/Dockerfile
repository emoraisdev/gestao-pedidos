FROM mongodb/mongodb-community-server:latest

COPY init.js /docker-entrypoint-initdb.d/

# Exponha a porta 27017 para que outras aplicações possam se conectar
EXPOSE 27017