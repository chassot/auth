# auth
JWT based authentication. 


serviços de autenticação e controle de usuários.

#Postgres On Docker
Utilize esta definição de docker-compose para rodar uma instância do postgres e pgadmin
```yaml
version: '3.5'

services:
  postgres:
    container_name: postgres_container
    image: postgres
    environment:
      - POSTGRES_PASSWORD=changeme
      - POSTGRES_USER=postgres
      - POSTGRES_DB=authdb
      - PGDATA: /data/postgres
    volumes:
       - postgres:/data/postgres
    ports:
      - "5432:5432"
    networks:
      - postgres
    restart: unless-stopped
  
  pgadmin:
    container_name: pgadmin_container
    image: dpage/pgadmin4
    environment:
      PGADMIN_DEFAULT_EMAIL: ${PGADMIN_DEFAULT_EMAIL:-pgadmin4@pgadmin.org}
      PGADMIN_DEFAULT_PASSWORD: ${PGADMIN_DEFAULT_PASSWORD:-admin}
    volumes:
       - pgadmin:/root/.pgadmin
    ports:
      - "${PGADMIN_PORT:-5050}:80"
    networks:
      - postgres
    restart: unless-stopped

networks:
  postgres:
    driver: bridge

volumes:
    postgres:
    pgadmin:
```

 #Auth on docker
 É possível realizar o build de uma imagem docker do serviço Auth. Para isto utilize o comando
 
 ```shell sudo docker build -t auth-image .```

obs: O comando de exemplo é utilizado na raiz do projeto.

A partir disto é possível utilizar a definição de docker-compose abaixo para rodar o postgres + serviço auth

```yaml
version: "3"
services:
  postgres:
    image: postgres:latest
    network_mode: bridge
    container_name: postgres
    volumes:
      - postgres-data:/var/lib/postgresql/data
    expose:
    - 5432
    ports:
      - 5432:5432
    environment:
         - POSTGRES_PASSWORD=changeme
         - POSTGRES_USER=postgres
         - POSTGRES_DB=authdb
    restart: unless-stopped
 
  pgadmin:
    container_name: pgadmin_container
    image: dpage/pgadmin4
    network_mode: bridge
    environment:
      PGADMIN_DEFAULT_EMAIL: ${PGADMIN_DEFAULT_EMAIL:-pgadmin4@pgadmin.org}
      PGADMIN_DEFAULT_PASSWORD: ${PGADMIN_DEFAULT_PASSWORD:-admin}            
    volumes:
       - pgadmin:/root/.pgadmin
    ports:
      - 5050:80
    depends_on:
      - postgres
    links:
      - postgres
    restart: unless-stopped
    
  
  authentication:
    image: auth-image:latest
    network_mode: bridge
    container_name: auth
    volumes:
      - ./logs:/app/auth/logs
    expose:
      - 8083
    ports:
      - 8083:8080
    depends_on:
      - postgres
    links:
      - postgres
    restart: unless-stopped

volumes:
  logs:
  postgres-data:         
  pgadmin: 
```