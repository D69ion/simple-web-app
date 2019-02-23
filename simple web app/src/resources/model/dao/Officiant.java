package resources.model.dao;

import java.util.HashSet;

public class Officiant {
    private int id;
    private String firstName;
    private String secondName;
    private HashSet<Order> orders;


    public Officiant() {
        this.id = 0;
        this.firstName = null;
        this.secondName = null;
        this.orders = new HashSet<>();
    }

    public Officiant(String firstName, String secondName, HashSet<Order> orders) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.orders = orders;
    }

    protected Officiant(String firstName, String secondName, HashSet<Order> orders, int id) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.orders = orders;
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public void add(Order order) {
        orders.add(order);
    }

    public boolean remove(Order order) {
        return orders.remove(order);
    }

    public boolean contains(Order order) {
        return orders.contains(order);
    }

    public HashSet<Order> getOrders() {
        return this.orders;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected void setId() {

    }

    public int getId() {
        return this.id;
    }

}
