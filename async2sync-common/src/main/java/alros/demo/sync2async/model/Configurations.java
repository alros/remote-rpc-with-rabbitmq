package alros.demo.sync2async.model;

public class Configurations {
	public static final String requestExchangeName = "requests-exchange";
	public static final String requestManualQueueName = "requests-manual";
	public static final String requestRpcQueueName = "requests-rpc";
	public static final String requestManualRoutingKey="request.manual";
	public static final String requestRpcRoutingKey="request.rpc";
	public static final String responseExchangeName = "response-exchange";
	public static final String responseQueueName = "response";
	public static final String responseManualRoutingKey="response.manual";
	public static final String responseRpcRoutingKey="response.rpc";
}
