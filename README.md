# Reminder

A simple app for sending reminders.


## Docker Compose


### Build

To build the app execute the following command:

```

> ./mvnw clean package -Ddocker-image.tag=latest

``` 

which will build and deploy the image into the local Docker registry.
 


### Run

The application expects a number of environment variables which 
should be provided within an .env file, and which should take 
the following format:

```

SMTP_HOST=...
SMTP_PORT=...
SMTP_USERNAME=...
SMTP_PASSWORD=...

```

To run the app execute the following command:

```

> docker compose -f deploy/docker/docker-compose.yml up

```



## Minikube


### Build

If you want to deploy the app within minikube you first need to 
make the docker registry within the minikube cluster accessible 
by executing the following command: 

```

> eval $(minikube docker-env)

```

after which you build the same as above:

```

> ./mvnw clean package -Ddocker-image.tag=latest

``` 

which will build and deploy the image into the cluster Docker 
registry. The application can be accessed by opening a browser 
[here](http://localhost:8080)



### Run

The deployment expects a number of environment variables within 
a secret called `smtp-secret` which can be created with the 
following command:

```

> kubectl create secret generic smtp-secret --from-env-file=.env

```

using the same env file as described above. To deploy the 
application you can run the provided deploy script:

```

> deploy/kubernetes/bin/deploy

```

To expose the application run the following command:

```

> kubectl port-forward service/reminder 8080:8080

```

Once deployed, running, and exposed, the application can be 
accessed by opening a browser [here](http://localhost:8080)
