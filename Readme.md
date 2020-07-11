#Follow these steps
1. Enable [Localstack](#Run LocalStack)
2. Use awscli-local for creating required resources
    - References
        - Install [awscli-local](https://github.com/localstack/awscli-local)
        - [Useful Commands](https://lobster1234.github.io/2017/04/05/working-with-localstack-command-line/)
            - we have to replace aws with awslocal in those commands
3. Run the [Application](#Run Application)

#Run LocalStack
`TMPDIR=${TMDIR}/tmp SERVICES=sqs,sts docker-compose up`

#Run Application
##Build Jar
`./gradlew bootJar`

##Build Docker Image
`docker build -t graceful-shutdown:latest .`

##Run Docker with image
`docker run -p 8090:8090 graceful-shutdown`

#Manual Application Shutdown
`curl -X POST localhost:port/actuator/shutdown` 