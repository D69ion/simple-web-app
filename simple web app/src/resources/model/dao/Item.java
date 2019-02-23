package resources.model.dao;

public class Item {
    private int id;
    private String name;
    private String description;
    private int cost;

    public Item() {
        this.id = 0;
        this.name = null;
        this.description = null;
        this.cost = 0;
    }

    public Item(String name, String description, int cost) {
        this.name = name;
        this. description = description;
        this.cost = cost;
    }

    protected Item(String name, String description, int cost, int id) {
        this.name = name;
        this. description = description;
        this.cost = cost;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return this.cost;
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
