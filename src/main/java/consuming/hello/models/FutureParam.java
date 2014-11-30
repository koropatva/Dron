package consuming.hello.models;

public class FutureParam {

    public FutureParam(final String key, final String dependence) {
        this.key = key;
        this.dependence = dependence;
    }

    private String key;

    private String dependence;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDependence() {
        return dependence;
    }

    public void setDependence(String dependence) {
        this.dependence = dependence;
    }
}
