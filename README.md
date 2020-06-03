
# Park Connect Demo App


## Pre-req
You have the following : 
- Kubernetes Cluster
- Mongo DB running 
- Helm  v 3.x.x with bitnami repo
- [Prometheus-Operator](https://github.com/bitnami/charts/tree/master/bitnami/prometheus-operator) 
- EFK stack 

## Setup

Create new namepace 


```bash
kubectl create cs park-connect
```

Change environment to park-connect

```bash
kubens park-connect
```

>Follow the below steps to deploy mongodb in kubernetes ( Only if you do not have mongodb )

Install Mongo DB
```bash
helm install podmongo \
        --set mongodbRootPassword=secretpassword, mongodbUsername=mongoadmin,mongodbPassword=secret mongodbDatabase=admin \
        bitnami/mongodb
```

Login to mongodb and run the below command


```bash
> use demo

> db.createCollection('location')
```

Go to `{WORKSPACE}/scripts/` and paste the content of **db.location.insert.sample**



## Deploy the app

Change environment to park-connect

```bash
kubens park-connect
```

navigate to `{WORKSPACE}\kubernetes` and run the following command 



```bash
kubectl create configmap app-cfg  --from-file=app.properties

kubectl create -f keys-config.yaml

# Remember to change the ingress host
kubectl create -f ingress.yaml

# if you have prometheus-operator installed, you need to go prometheus namespace and create service monitor
# remember to change the namespace and portname to monitor.
kubectl create -f svm-parkconnect.yaml

```


Open new terminal and navigate to `{WORKSPACE}` and run the following command 

```bash
skaffold dev
```

Open another terminal and navigate to `{WORKSPACE}` and run the following command 

```bash
mvn clean package
```