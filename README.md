# FakeLoad App
A small client-server application to perform system load simulations using the [FakeLoad library](https://github.com/msigwart/fakeload).

Server and client communicate over RabbitMQ. Server listens to simulation request and performs system load simulations, the client can send simulation requests to the server.

