package resources.model;

import resources.model.dao.Item;

import java.util.Collection;

public interface ItemsDAO {
    public boolean insert(Item item);

    public boolean delete(Item item);

    public Item findByID(int id);

    public boolean update(Item item);

    public boolean saveOrUpdate(Item item);

    public Collection<Item> findByName(String name);

    public Collection<Item> findByCost(Integer cost);

    public Collection<Item> getAll();
}
