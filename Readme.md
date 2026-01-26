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
