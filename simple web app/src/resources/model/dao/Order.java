package resources.model.dao;

import java.time.LocalDate;
import java.util.HashMap;

public class Order {
    private int id;
    private LocalDate date;
    private Officiant officiant;
    private HashMap<Item, Integer> items;

    public Order() {
        this.id = 0;
        this.date = null;
        this.officiant = null;
        this.items = new HashMap<>();
    }

    public Order(Officiant officiant, LocalDate date, HashMap<Item, Integer> items) {
        this.officiant = officiant;
        this.date = date;
        this.items = items;
    }

    protected Order(Officiant officiant, LocalDate date, HashMap<Item, Integer> items, int id) {
        this.officiant = officiant;
        this.date = date;
        this.items = items;
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Officiant getOfficiant() {
        return this.officiant;
    }

    public void setOfficiant(Officiant officiant) {
        this.officiant = officiant;
    }

    public void addItem(Item item) {
        addItem(item, 1);
    }

    public void addItem(Item item, int quantity) {
        if(items.containsKey(item)){
            items.replace(item, items.get(item) + quantity);
        }
        else{
            items.put(item, quantity);
        }
    }

    public void setQuantity(Item item, int quantity) {
        items.replace(item, quantity);
    }

    public void remove(Item item) {
        items.remove(item);
    }

    public HashMap<Item, Integer> getItemsMap() {
        return this.items;
    }

    protected void setId() {

    }

    protected void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

}
