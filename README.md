# remote-rpc-with-rabbitmq

This is a demo to show how to use RabbitMQ for RPC.

There is a frontend exposing REST API, a server performing the elaboration, and they interact via RabbitMQ (defaults on docker).

TestWithRandomCalls in async2sync-client/src/integration/java/alros/demo/sync2async/client/ demonstrate the flow.

Check [my article on medium](https://medium.com/geekculture/remote-rpc-based-on-messaging-f3d1646ae373) for details.
