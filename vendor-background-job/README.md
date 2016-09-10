# Vendor Background Job

###Load data to Redis

* `../gradlew bootRun -Predis='load'`


###Clean data from Redis


* `../gradlew bootRun -Predis='clean'`


### Start Dummy Vendor and Orchestrator Service

*   Goto  sb/http-server And Run
* `../gradlew clean build bootRun`


### Start Confirm Vendor Background Job

* Goto sb/vendor-background-job And Run
* `../gradlew clean build bootRun`

* You Done, Now  monitor logs
