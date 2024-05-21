package lk.ijse.model;

public class MedicineSupplier {

    private String id;
    private String mId;
    private String date;

    public MedicineSupplier() {
    }

    public MedicineSupplier(String id, String mId, String date) {
        this.id = id;
        this.mId = mId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MedicineSupplier{" +
                "id='" + id + '\'' +
                ", mId='" + mId + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
