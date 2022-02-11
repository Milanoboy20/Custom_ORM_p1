package testmodels;

import annotations.primaryKey;

public class TableTop {

    @primaryKey
    private int tableID;

    private int tableSize;
    private String tableSide;
    private boolean available;

    public TableTop(){
        super();
    }

    public TableTop(int tableID, int tableSize, String tableSide, boolean available){
        this.tableID   = tableID;
        this.tableSize = tableSize;
        this.tableSide = tableSide;
        this.available = available;
    }

    public TableTop(int tableSize, String tableSide, boolean available){
        this.tableSize = tableSize;
        this.tableSide = tableSide;
        this.available = available;
    }

    public int getId() {
        return tableID;
    }

    public void setId(int id) {
        this.tableID = id;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public String getTableSide() {
        return tableSide;
    }

    public void setTableSide(String tableSide) {
        this.tableSide = tableSide;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "TableTop{" +
                "id=" + tableID +
                ", tableSize=" + tableSize +
                ", tableSide='" + tableSide + '\'' +
                ", available=" + available +
                '}';
    }
}
