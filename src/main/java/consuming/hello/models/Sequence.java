package consuming.hello.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import consuming.hello.exceptions.EmptyDataException;
import consuming.hello.services.RESTfullService;

public class Sequence {

    private List<Plugin> plugins = new LinkedList<Plugin>();

    private RESTfullService restFullService = RESTfullService.getInstance();

    private List<Param> params = new ArrayList<Param>();

    private final ObjectMapper mapper = new ObjectMapper();

    public void runSequence() throws EmptyDataException {
        for (Plugin plugin : plugins) {
            String response = restFullService.run(plugin);
            System.out.println(response);
            try {
                fillFutureParams(response, plugin.getFutureParams());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void fillFutureParams(String response, List<FutureParam> futureParams)
            throws JsonProcessingException, IOException {
        for (FutureParam futureParam : futureParams) {

            JsonNode node = mapper.readTree(response);
            fillFutureParam(node, futureParam.getKey(), futureParam.getDependence());
        }
    }

    private void fillFutureParam(JsonNode node, String roadKey, String roadValue) {
        while (roadValue.contains(".")) {
            List<Integer> listNodeIndexes = new ArrayList<Integer>();

            // Get Node name
            String nodeName = roadValue.substring(0, roadValue.indexOf("."));
            // Update roadValue without current node name
            roadValue = roadValue.substring(roadValue.indexOf(".") + 1);

            if (nodeName.contains("[")) {
                // Select all indexes
                String roadIndexes =
                        nodeName.substring(nodeName.indexOf("[") + 1, nodeName.indexOf("]"));
                for (String index : roadIndexes.split(",")) {
                    listNodeIndexes.add(Integer.parseInt(index));
                }
                // Get node key
                nodeName = nodeName.substring(0, nodeName.indexOf("["));

                if (!nodeName.isEmpty()) {
                    node = node.findPath(nodeName);
                }
                int index = 0;
                for (Iterator<JsonNode> iterator = node.iterator(); iterator.hasNext();) {
                    JsonNode iteratorNode = iterator.next();

                    if (listNodeIndexes.contains(index)) {
                        fillFutureParam(iteratorNode, roadKey, roadValue);
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

        addParamToList(node, roadKey);
    }

    private void addParamToList(JsonNode node, String roadKey) {
        // Try to search if we have it param in the list
        Param currentParam = null;
        for (Param param : params) {
            if (param.getKey().equals(roadKey)) {
                currentParam = param;
                break;
            }
        }

        // If the param presents, just update it
        if (currentParam != null) {
            currentParam.setValue(node.asText());
            System.out.println("Added new param with key = " + roadKey + " and value = "
                    + currentParam.getValue());
        } else {
            // Other way added new
            addParam(new Param(roadKey, node.asText()));
            System.out.println("Added new param with key = " + roadKey + " and value = "
                    + node.asText());
        }
    }

    public void addPlugin(Plugin plugin) {
        plugin.setSequence(this);
        plugins.add(plugin);
    }

    public void addParam(Param param) {
        params.add(param);
    }

    public void updateParam(Param param) {
        for (Param par : params) {
            if (par.getKey().equals(param.getKey())) {
                par.setValue(param.getValue());
            }
        }
    }

    public List<Param> getParams() {
        return params;
    }
}
