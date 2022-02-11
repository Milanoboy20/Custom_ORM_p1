package ormTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import orm.ORM;
import testmodels.Customer;
import testmodels.Reservation;
import testmodels.TableTop;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ORMTests {


    private static Customer customer, cus;
    private static TableTop table;
    private static Reservation rev;
    private static ORM<Customer> cusDB;
    private static ORM<TableTop> top;
    private static ORM<Reservation> resv;

    static {
        try {
            cusDB = new ORM<>(Customer.class);
            top   = new ORM<>(TableTop.class);
            resv  = new ORM<>(Reservation.class);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeAll
    public static void beforeAll(){
        System.out.println("Before the Tests start!");
        customer = new Customer("Firstname", "Lastname", "0123456789", "name@world.com");
        table    = new TableTop(6, "Upper", true);
        rev      = new Reservation();
    }


    @AfterEach
    public void afterEach(){
        System.out.println("After each Test Print!");
    }


    @AfterAll
    public static void afterAll(){
        System.out.println("After All Tests are done Print!");
    }


    @Test
    public void addData() throws IllegalAccessException {
        System.out.println("\nNew data insertion Test");
        System.out.println("This data will be added to DB");
        System.out.println(customer);
        cus = cusDB.create(customer);
        assertInstanceOf(Customer.class, cus);
        System.out.println("\nCustomer added to database now has an ID");
        System.out.println(cus);
        System.out.println("\nList of current data with newly added data present");
        List<Customer> cuzz = cusDB.selectAll(cus);
        for (Customer c : cuzz){
            System.out.println(c);
        }
        System.out.println("If added data is present(persists), Test passed!");
    }


    @Test
    public void allCurrentData(){
        System.out.println("\nList of current data in DB");
        List<Customer> cuzz = cusDB.selectAll(cus);
        for (Customer c : cuzz){
            System.out.println(c);
        }
    }


    @Test
    public void getData() throws IllegalAccessException {
        System.out.println("\nRetrieving data from DB");
        Customer cus1 = cusDB.select(cus);
        Customer cus2 = cusDB.selectByID(new Customer(), cus1.getId());
        assertInstanceOf(Customer.class, cus1);
        System.out.println("Customer now has ID");
        System.out.println(cus1);
        System.out.println("Test passed!");
    }


    @Test
    public void updateData() throws IllegalAccessException {
        System.out.println("\nUpdating newly added data in DB");
        cus.setPhone("0000000000");
        cusDB.update(cus);
        System.out.println("Updated customer data:");
        System.out.println(cus);
    }


    @Test
    public void deleteData() throws IllegalAccessException {
        System.out.println("\nRemoving newly added customer from DB");
        Customer cus1 = cusDB.delete(cus);
        assertInstanceOf(Customer.class, cus1);
        System.out.println("Removed customer data:");
        System.out.println(cus1);
        System.out.println("\nList of current data after deletion");
        List<Customer> cuzz = cusDB.selectAll(cus);
        for (Customer c : cuzz){
            System.out.println(c);
        }
        System.out.println("If added data is not present(deleted), Test passed!");
    }


}
