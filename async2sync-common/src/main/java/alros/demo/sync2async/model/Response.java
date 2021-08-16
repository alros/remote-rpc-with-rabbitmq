package alros.demo.sync2async.model;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Response PROCESSING_ERROR = new Response("processing error");

	private final String correlationId;
	private final int value;
	private final String error;

	public Response(String correlationId, int value) {
		this.correlationId = correlationId;
		this.value = value;
		this.error = null;
	}

	private Response(String error) {
		this.correlationId = null;
		this.value = 0;
		this.error = error;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public int getValue() {
		return value;
	}

	public String getError() {
		return error;
	}

}
