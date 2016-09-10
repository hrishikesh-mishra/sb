# Vendor Background Job

## Start Redis Server
    redis-server

## Redis Sentinel config file (s.conf)
    sentinel monitor mymaster 127.0.0.1 6379 1
    sentinel auth-pass mymaster foobared
    sentinel down-after-milliseconds mymaster 60000
    sentinel failover-timeout mymaster 180000
    sentinel parallel-syncs mymaster 1

## Start Redis Sentinel
     redis-server s.conf --sentinel

## Clean dummy data from Redis


    * ../gradlew clean build bootRun -Predis='clean'


## Load dummy data to Redis

    * ../gradlew clean build bootRun -Predis='load'



### Start Dummy Vendor and Orchestrator Service

    *   Goto  sb/http-server And Run
    * ../gradlew clean build bootRun


### Start Confirm Vendor Background Job

    * Goto sb/vendor-background-job And Run
    * ../gradlew clean build bootRun

    * You Done, Now  monitor logs
