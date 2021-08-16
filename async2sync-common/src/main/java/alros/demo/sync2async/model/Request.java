package alros.demo.sync2async.model;

import java.io.Serializable;

public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String correlationId;
	private final int value;

	public Request(String correlationId, int value) {
		this.correlationId = correlationId;
		this.value = value;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public int getValue() {
		return value;
	}

}
