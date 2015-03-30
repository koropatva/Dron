package com.dron.sender.sequence.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.sequence.models.FutureParam;
import com.dron.sender.sequence.models.Param;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SequenceService {

	public static final String ROAD_SEPARATOR = "..";

	private RESTfullService restFullService = RESTfullService.getInstance();

	private final ObjectMapper mapper = new ObjectMapper();

	private Sequence sequence;

	public SequenceService(Sequence sequence) {
		this.sequence = sequence;
	}

	public void runSequence() throws DronSenderException {
		for (int i = 0; i < sequence.getPlugins().size(); i++) {
			Plugin plugin = sequence.getPlugins().get(i);
			plugin.setSequence(sequence);
			String response = restFullService.run(plugin);
			plugin.setResponce(response);
			System.out.println(response);
			fillFutureParams(response, plugin.getFutureParams());
		}
	}

	private void fillFutureParams(String response,
			List<FutureParam> futureParams) {
		for (FutureParam futureParam : futureParams) {
			try {
				JsonNode node = mapper.readTree(response);
				fillFutureParam(node, futureParam, futureParam.getDependence());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void fillFutureParam(JsonNode node, FutureParam futureParam,
			String roadValue) {
		while (roadValue.contains(ROAD_SEPARATOR)) {
			List<Integer> listNodeIndexes = new ArrayList<Integer>();

			// Get Node name
			String nodeName = roadValue.substring(0,
					roadValue.indexOf(ROAD_SEPARATOR));
			// Update roadValue without current node name
			roadValue = roadValue.substring(roadValue.indexOf(ROAD_SEPARATOR)
					+ ROAD_SEPARATOR.length());

			if (nodeName.contains("[")) {
				// Select all indexes
				String roadIndexes = nodeName.substring(
						nodeName.indexOf("[") + 1, nodeName.indexOf("]"));
				for (String index : roadIndexes.split(",")) {
					listNodeIndexes.add(Integer.parseInt(index.trim()));
				}
				// Get node key
				nodeName = nodeName.substring(0, nodeName.indexOf("["));

				if (!nodeName.isEmpty()) {
					node = node.findPath(nodeName);
				}
				int index = 0;
				for (Iterator<JsonNode> iterator = node.iterator(); iterator
						.hasNext();) {
					JsonNode iteratorNode = iterator.next();

					if (listNodeIndexes.contains(index)) {
						fillFutureParam(iteratorNode, futureParam, roadValue);
					}
					index++;
				}
			}
			if (node == null || nodeName == null || !node.hasNonNull(nodeName)) {
				return;
			}
			node = node.get(nodeName);
		}
		if (node == null || roadValue == null || !node.hasNonNull(roadValue)) {
			return;
		}
		node = node.get(roadValue);

		addParamToList(node, futureParam);
	}

	private void addParamToList(JsonNode node, FutureParam futureParam) {
		// Try to search if we have it param in the list
		Param currentParam = null;
		for (Param param : sequence.getParams()) {
			if (param.getKey().equals(futureParam.getKey())) {
				currentParam = param;
				break;
			}
		}

		// If the param presents, just update it
		if (currentParam != null) {
			currentParam.setValue(node.asText());
			System.out.println("Added new param with key = "
					+ futureParam.getKey() + " and value = "
					+ currentParam.getValue());
		} else {
			// Other way added new
			sequence.addParam(new Param(futureParam.getKey(), node.asText()));
			System.out.println("Added new param with key = "
					+ futureParam.getKey() + " and value = " + node.asText());
		}
	}

}
