::Change spring.datasource.url in application.properties jdbc:postgresql://nas.karel.be:40355/postgres => jdbc:postgresql://postgres:5432/postgres
mvn package -DskipTests &::create executable jar file
docker build -t snakehead007/gip4 . &:: build the docker image
docker push snakehead007/gip4 &:: upload the docker image