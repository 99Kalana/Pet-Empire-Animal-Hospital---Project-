package lk.ijse.model;

public class BillsDetails {

    private String billId;
    private String medicineId;
    private int medicineQty;
    private double medicinePrice;

    public BillsDetails(String bill_id, String medicine_id, String medicine_qty, String medicine_price) {
    }

    public BillsDetails(String billId, String medicineId, int medicineQty, double medicinePrice) {
        this.billId = billId;
        this.medicineId = medicineId;
        this.medicineQty = medicineQty;
        this.medicinePrice = medicinePrice;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getMedicineQty() {
        return medicineQty;
    }

    public void setMedicineQty(int medicineQty) {
        this.medicineQty = medicineQty;
    }

    public double getMedicinePrice() {
        return medicinePrice;
    }

    public void setMedicinePrice(double medicinePrice) {
        this.medicinePrice = medicinePrice;
    }

    @Override
    public String toString() {
        return "BillsDetails{" +
                "billId='" + billId + '\'' +
                ", medicineId='" + medicineId + '\'' +
                ", medicineQty=" + medicineQty +
                ", medicinePrice=" + medicinePrice +
                '}';
    }

}
