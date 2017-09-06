# FakeLoad App
A small client-server application to perform system load simulations using the [FakeLoad](https://github.com/msigwart/fakeload) library.

Server and client communicate over RabbitMQ. The [server](https://github.com/msigwart/fakeload-app/tree/master/fakeload-server) 
listens to simulation request and performs system load simulations. 
The [client](https://github.com/msigwart/fakeload-app/tree/master/fakeload-client) 
can send simulation requests to the server.

