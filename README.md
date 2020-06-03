docker run -p 28888:27017 \
    -e MONGO_INITDB_ROOT_USERNAME=mongoadmin \
    -e MONGO_INITDB_ROOT_PASSWORD=secret \
    mongo


mongoimport --drop -d demo  -c location --type json --jsonArray --file ./location.json $*


mongoimport --drop -d demo  \
            --authenticationDatabase admin \
            -u mongoadmin -p secret \
            --port 28888 -c location \
            --type json --jsonArray --file ./location.json $*




--- Paketo 
pack build paketo-parkc -p . --builder gcr.io/paketo-buildpacks/builder:base








============================== MONGO DB ==========================================
db.createCollection( location)

helm install podmongo \
  --set mongodbRootPassword=secretpassword,mongodbUsername=mongoadmin,mongodbPassword=secret,mongodbDatabase=admin \
    bitnami/mongodb


** Please be patient while the chart is being deployed **

MongoDB can be accessed via port 27017 on the following DNS name from within your cluster:
    podmongo-mongodb.rob-workshop.svc.cluster.local

To get the root password run:

    export MONGODB_ROOT_PASSWORD=$(kubectl get secret --namespace rob-workshop podmongo-mongodb -o jsonpath="{.data.mongodb-root-password}" | base64 --decode)

To get the password for "mongoadmin" run:

    export MONGODB_PASSWORD=$(kubectl get secret --namespace rob-workshop podmongo-mongodb -o jsonpath="{.data.mongodb-password}" | base64 --decode)

To connect to your database run the following command:

    kubectl run --namespace rob-workshop podmongo-mongodb-client --rm --tty -i --restart='Never' --image docker.io/bitnami/mongodb:4.2.6-debian-10-r34 --command -- mongo admin --host podmongo-mongodb --authenticationDatabase admin -u root -p $MONGODB_ROOT_PASSWORD

To connect to your database from outside the cluster execute the following commands:

    kubectl port-forward --namespace rob-workshop svc/podmongo-mongodb 27017:27017 &
    mongo --host 127.0.0.1 --authenticationDatabase admin -p $MONGODB_ROOT_PASSWORD





docker push robinfoe/park-connect:tagname




---- IGNORE --- 

How to deploy ?? 

TODO :: 
Jolokia add on with hawtio
Skaffold with dockerfile 
    - need to write deployment, 
    - service
    - route
    - configmap 
    - secret ( base 64 jks )
