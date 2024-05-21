package lk.ijse.model;

public class Client {

    private String id;
    private String petId;
    private String name;
    private String address;
    private String email;
    private int contactNo;

    public Client() {
    }

    public Client(String id, String petId, String name, String address, String email, int contactNo) {
        this.id = id;
        this.petId = petId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contactNo = contactNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", petId='" + petId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contactNo=" + contactNo +
                '}';
    }
}
