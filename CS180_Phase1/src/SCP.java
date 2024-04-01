public class SCP {
    private String scpID;
    private String name;
    private String containmentStatus;

    public SCP(String scpID, String name, String containmentStatus) {
        this.scpID = scpID;
        this.name = name;
        this.containmentStatus = containmentStatus;
    }

    // Getters
    public String getScpID() {
        return scpID;
    }

    public String getName() {
        return name;
    }

    public String getContainmentStatus() {
        return containmentStatus;
    }

    // Setters
    public void setScpID(String scpID) {
        this.scpID = scpID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContainmentStatus(String containmentStatus) {
        this.containmentStatus = containmentStatus;
    }

    @Override
    public String toString() {
        return "SCP{" +
                "scpID='" + scpID + '\'' +
                ", name='" + name + '\'' +
                ", containmentStatus='" + containmentStatus + '\'' +
                '}';
    }
}
