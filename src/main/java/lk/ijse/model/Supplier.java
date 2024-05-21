package lk.ijse.model;

public class Supplier {

    private String id;
    private String name;
    private int contactNo;
    private String location;
    private String email;
    private String productType;
    private int qtyOnHand;

    public Supplier() {
    }

    public Supplier(String id, String name, int contactNo, String location, String email, String productType, int qtyOnHand) {
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
        this.location = location;
        this.email = email;
        this.productType = productType;
        this.qtyOnHand = qtyOnHand;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contactNo=" + contactNo +
                ", location='" + location + '\'' +
                ", email='" + email + '\'' +
                ", productType='" + productType + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
