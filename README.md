# FakeLoad App
A small client-server application to perform system load simulations using the [FakeLoad](https://github.com/msigwart/fakeload) library.

Server and client communicate over RabbitMQ. Server listens to simulation request and performs system load simulations, the client can send simulation requests to the server.

