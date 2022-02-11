package testmodels;

import annotations.primaryKey;

public class Reservation {

    @primaryKey
    private int reservationID;

    private int customerID, tableID;
    private long reservationDate;

    public Reservation(){
        super();
    }

    public Reservation(int reservationID, int customerID, int tableID, long date) {
        this.reservationID       = reservationID;
        this.customerID          = customerID;
        this.tableID             = tableID;
        this.reservationDate     = date;
    }

    public Reservation(int customerID, int tableID, long date) {
        this.customerID          = customerID;
        this.tableID             = tableID;
        this.reservationDate     = date;
    }

    public int getId() {
        return reservationID;
    }

    public void setId(int id) {
        this.reservationID = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public long getDate() {
        return reservationDate;
    }

    public void setDate(long date) {
        this.reservationDate = date;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + reservationID +
                ", customerID=" + customerID +
                ", tableID=" + tableID +
                ", date=" + reservationDate +
                '}';
    }
}
