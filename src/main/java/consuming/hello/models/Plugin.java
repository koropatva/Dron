package consuming.hello.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import consuming.hello.utils.ParamsUtils;

public class Plugin {

    public Plugin(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);

        futureParams = new ArrayList<FutureParam>();
    }

    private Sequence sequence;

    private List<MediaType> mediaTypes;

    private HttpHeaders headers;

    private HttpMethod httpMethod;

    private String url;

    private String postBody;

    private String request;

    private String responce;

    /**
     * Structure of parameter way looks like: names of JSON object separated by dot. If you need to
     * select someone object from the list you should to use "[numbers separated by comma]"
     */
    private List<FutureParam> futureParams;

    public void addFutureParam(String key, String dependence) {
        sequence.addParam(new Param(key, null));
        this.futureParams.add(new FutureParam(key, dependence));
    }

    public void addFutureParamList(String key, String dependence) {
        sequence.addParam(new Param(key, null, true));
        this.futureParams.add(new FutureParam(key, dependence));
    }

    public HttpEntity<String> fillEntity() {
        return new HttpEntity<String>(ParamsUtils.fillDataParams(postBody, sequence.getParams()),
                headers);
    }

    public String fillUrl() {
        return ParamsUtils.fillDataParams(url, sequence.getParams());
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public List<MediaType> getMediaTypes() {
        return mediaTypes;
    }

    public void setMediaTypes(List<MediaType> mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }

    public String getRequest() {
        return request;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public List<FutureParam> getFutureParams() {
        return futureParams;
    }

}
