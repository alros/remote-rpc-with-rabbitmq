package alros.demo.sync2async.client;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alros.demo.sync2async.model.Request;
import alros.demo.sync2async.model.Response;

@RestController
public class RestService {

	private BackendInvocationService backendInvocation;

	public RestService(BackendInvocationService backendInvocationService) {
		this.backendInvocation = backendInvocationService;
	}

	@GetMapping("/cube")
	public Response cube(@RequestParam(value = "value", defaultValue = "1") int value) {
		return backendInvocation.manuallyInvokeCube(new Request(UUID.randomUUID().toString(), value));
	}

	@GetMapping("/cube-rpc")
	public Response cubeRpc(@RequestParam(value = "value", defaultValue = "1") int value) {
		return backendInvocation.rpcInvokeSquare(new Request(null, value));
	}
}
