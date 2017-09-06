# FakeLoad Client
A commandline application which can send FakeLoad requests to a 
[FakeLoad Server](https://github.com/msigwart/fakeload-app/tree/master/fakeload-server).

## Get Started
You can start the client file by executing:
```
$ cd fakeload-client
$ mvn clean install
$ java -jar target/fakeload-client-0.1-SNAPSHOT-jar-with-dependencies.jar -h <hostname> -q <queuename>
```

After that follow the instructions to create a FakeLoad request.