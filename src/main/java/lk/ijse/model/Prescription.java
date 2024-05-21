package lk.ijse.model;

public class Prescription {

    private String id;
    private String type;
    private String veterinarianId;

    public Prescription() {
    }

    public Prescription(String id, String type, String veterinarianId) {
        this.id = id;
        this.type = type;
        this.veterinarianId = veterinarianId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVeterinarianId() {
        return veterinarianId;
    }

    public void setVeterinarianId(String veterinarianId) {
        this.veterinarianId = veterinarianId;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", veterinarianId='" + veterinarianId + '\'' +
                '}';
    }
}
