package com.dron.sender.sequence.services;

import org.springframework.web.client.RestTemplate;

import com.dron.sender.exceptions.EmptyDataException;
import com.dron.sender.sequence.models.Plugin;

public class RESTfullService {

    private static RESTfullService resTfullService = new RESTfullService();

    public static RESTfullService getInstance() {
        return resTfullService;
    }

    private RestTemplate restTemplate = new RestTemplate();

    public String run(Plugin plugin) throws EmptyDataException {
        return run(plugin, String.class);
    }

    public <T> T run(Plugin plugin, Class<T> type) throws EmptyDataException {
        switch (plugin.getHttpMethod()) {
            case POST:
                return restTemplate.postForObject(plugin.fillUrl(), plugin.fillEntity(), type);
            case GET:
                return restTemplate.getForObject(plugin.fillUrl(), type);
            default:
                throw new EmptyDataException("");
        }
    }

}
