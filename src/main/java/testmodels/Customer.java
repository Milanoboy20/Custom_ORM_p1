package testmodels;

import annotations.primaryKey;
import annotations.table;

@table
public class Customer {

    @primaryKey
    private int customerID;

    private String firstname, lastname, phone, email;

    public Customer(){
        super();
    }

    public Customer(int id, String firstname, String lastname, String phone, String email){
        this.firstname = firstname;
        this.lastname  = lastname;
        this.phone     = phone;
        this.email     = email;
        this.customerID        = id;
    }

    public Customer(String firstname, String lastname, String phone, String email){
        this.firstname = firstname;
        this.lastname  = lastname;
        this.phone     = phone;
        this.email     = email;
    }

    public int getId() {
        return customerID;
    }

    public void setId(int id) {
        this.customerID = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + customerID +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
