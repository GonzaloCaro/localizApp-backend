# microservicios

### run

`mvn spring-boot:run`

### Limpiar compilación previa

`mvn clean`

### Limpiar + compilar

`mvn clean install`

### instale dependencias + compile:

`mvn clean package`

### Correr Test

`mvn clean verify`

### Crear usuario BD:

```
CREATE USER gestion_transporte IDENTIFIED BY "Caroorion1780*"
DEFAULT TABLESPACE "DATA"
TEMPORARY TABLESPACE "TEMP";
ALTER USER gestion_transporte QUOTA UNLIMITED ON DATA;
GRANT CREATE SESSION TO gestion_transporte;
GRANT "RESOURCE" TO gestion_transporte;
ALTER USER gestion_transporte DEFAULT ROLE "RESOURCE";
```

### Traer imagen docker

```
sudo docker pull gonzaduoc/img_cn1_gestion_usuarios
```

### Ver logs docker

```
sudo docker logs -f gestion-usuarios
```

### Ver contenedores docker

```
sudo docker ps -a
```

sudo docker stop gestion-usuarios
sudo docker rm gestion-usuarios

sudo docker stop gestion-transporte
sudo docker rm gestion-transporte

sudo docker stop bff
sudo docker rm bff


sudo docker run -d -p 8080:8080 \
  --name gestion-usuarios \
  -v "/home/ec2-user/wallet_oracle:/app/wallet" \
  -e "SPRING_DATASOURCE_URL=jdbc:oracle:thin:@cxtjowjkr0mdsxfa_high?TNS_ADMIN=/app/wallet" \
  gonzaduoc/img_cn1_gestion_usuarios:latest

  sudo docker run -d -p 8081:8081 \
  --name gestion-transporte \
  -v "/home/ec2-user/wallet_oracle:/app/wallet" \
  -e "SPRING_DATASOURCE_URL=jdbc:oracle:thin:@cxtjowjkr0mdsxfa_high?TNS_ADMIN=/app/wallet" \
  gonzaduoc/gestion-transporte:latest

  sudo docker run -d -p 8082:8082 --name bff gonzaduoc/bff:latest