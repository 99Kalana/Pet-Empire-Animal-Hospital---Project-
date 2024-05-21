package lk.ijse.model;

public class Appointment {

    private String id;
    private String clientId;
    private int no;
    private String date;
    private String time;

    public Appointment() {
    }

    public Appointment(String id, String clientId, int no, String date, String time) {
        this.id = id;
        this.clientId = clientId;
        this.no = no;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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
        return "Appointment{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", no=" + no +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
