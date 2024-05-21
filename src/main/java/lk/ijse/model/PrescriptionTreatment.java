package lk.ijse.model;

public class PrescriptionTreatment {

    private String pId;
    private String tId;
    private double price;
    private String date;
    private String time;

    public PrescriptionTreatment() {
    }

    public PrescriptionTreatment(String pId, String tId, double price, String date, String time) {
        this.pId = pId;
        this.tId = tId;
        this.price = price;
        this.date = date;
        this.time = time;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "PrescriptionTreatment{" +
                "pId='" + pId + '\'' +
                ", tId='" + tId + '\'' +
                ", price=" + price +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
