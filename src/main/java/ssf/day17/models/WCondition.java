package ssf.day17.models;

public class WCondition {
    private String condition;
    private String description;
    private String iconURL;

    @Override
    public String toString() {
        return "WCondition [condition=" + condition + ", description=" + description + ", iconURL=" + iconURL + "]";
    }
    
    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIconURL() {
        return iconURL;
    }
    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }
}
