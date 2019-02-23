package resources.model.dao;

import data.storage.DataSourceFactory;
import resources.model.ItemsDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ItemsDAOImpl implements ItemsDAO {
    private static DataSource dataSource;

    public ItemsDAOImpl(){
        dataSource = DataSourceFactory.createDataSource();
    }

    @Override
    public boolean insert(Item item) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into items values (?,?,?,?)")) {
            preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setString(3, item.getDescription());
            preparedStatement.setInt(4, item.getCost());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Item item) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from items where id = ?")) {
            preparedStatement.setInt(1, item.getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Item findByID(int id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from items where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if(!result.next())
                return null;
            Item item = new Item(result.getString("name"), result.getString("description"), result.getInt("cost"), result.getInt("id"));
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Item item) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update items set name = ?, description = ?, cost = ? where id = ?")) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setInt(3, item.getCost());
            preparedStatement.setInt(4, item.getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveOrUpdate(Item item) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from items where id = ?")) {
            preparedStatement.setInt(1, item.getId());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return update(item);
            }
            else{
                return insert(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Item> findByName(String name) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from items where name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            ArrayList<Item> items = new ArrayList<>();
            while(result.next()){
                items.add(new Item(result.getString("name"), result.getString("description"), result.getInt("cost"), result.getInt("id")));
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Item> findByCost(Integer cost) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from items where cost = ?")) {
            preparedStatement.setInt(1, cost);
            ResultSet result = preparedStatement.executeQuery();
            ArrayList<Item> items = new ArrayList<>();
            while(result.next()){
                items.add(new Item(result.getString("name"), result.getString("description"), result.getInt("cost"), result.getInt("id")));
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Item> getAll(){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from items")) {
            ResultSet result = preparedStatement.executeQuery();
            ArrayList<Item> items = new ArrayList<>();
            while(result.next()){
                items.add(new Item(result.getString("name"), result.getString("description"), result.getInt("cost"), result.getInt("id")));
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
