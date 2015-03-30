package com.dron.sender.sequence.services;

import org.springframework.web.client.RestTemplate;

import com.dron.sender.exceptions.HandlerNotReadyException;
import com.dron.sender.sequence.models.Plugin;

public class RESTfullService {

	private static RESTfullService resTfullService;

	private RESTfullService() {
	}

	public static RESTfullService getInstance() {
		if (resTfullService == null) {
			synchronized (RESTfullService.class) {
				if (resTfullService == null) {
					resTfullService = new RESTfullService();
				}
			}
		}
		return resTfullService;
	}

	private RestTemplate restTemplate = new RestTemplate();

	public String run(Plugin plugin) throws HandlerNotReadyException {
		return run(plugin, String.class);
	}

	public <T> T run(Plugin plugin, Class<T> type)
			throws HandlerNotReadyException {
		switch (plugin.getHttpMethod()) {
			case POST:
				return restTemplate.postForObject(plugin.fillUrl(),
						plugin.fillEntity(), type);
			case GET:
				return restTemplate.getForObject(plugin.fillUrl(), type);
			default:
				throw new HandlerNotReadyException(plugin.getHttpMethod()
						+ " is not maintained for now");
		}
	}

}
