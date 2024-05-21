package lk.ijse.model;

public class Veterinarian {

    private String id;
    private String name;
    private int contactNo;
    private String email;

    public Veterinarian() {
    }

    public Veterinarian(String id, String name, int contactNo, String email) {
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Veterinarian{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contactNo=" + contactNo +
                ", email='" + email + '\'' +
                '}';
    }
}
