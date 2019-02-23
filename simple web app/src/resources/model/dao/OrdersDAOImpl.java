package resources.model.dao;

import data.storage.DataSourceFactory;
import resources.model.OrdersDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class OrdersDAOImpl implements OrdersDAO {
    private static DataSource dataSource;

    public OrdersDAOImpl(){
        dataSource = DataSourceFactory.createDataSource();
    }

    @Override
    public boolean insert(Order order) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into orders values(?,?,?)")) {
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setDate(2, Date.valueOf(order.getDate()));
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Order order) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from orders where id = ?")) {
            preparedStatement.setInt(1, order.getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Order findByID(int id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement orderStatement = connection.prepareStatement("select * from orders where id = ?");
            ) {
            orderStatement.setInt(1, id);
            ResultSet resultOrder = orderStatement.executeQuery();
            if(!resultOrder.next()){
                return null;
            }
            return getOrderInfo(resultOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Order order) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("u")) {
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveOrUpdate(Order order) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where id = ?")) {
            preparedStatement.setInt(1, order.getId());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return update(order);
            }
            else{
                return insert(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Order> findByDate(LocalDate date) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement orderStatement = connection.prepareStatement("select * from orders where date(date) = ?");
            ) {
            orderStatement.setDate(1, Date.valueOf(date));
            ResultSet orderResult = orderStatement.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while(orderResult.next()){
                orders.add(getOrderInfo(orderResult));
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Order> findByOfficiant(Officiant officiant) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement orderStatement = connection.prepareStatement("select * from orders where officiant_id = ?")
        ) {
            orderStatement.setInt(1, officiant.getId());
            ResultSet orderResult = orderStatement.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (orderResult.next()){
                orders.add(getOrderInfo(orderResult));
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Order getOrderInfo(ResultSet orderResult){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement officiantStatement = connection.prepareStatement("select * from officiants where id = ?");
            PreparedStatement dictionaryStatement = connection.prepareStatement("select items_dictionary_id, quantity from items_orders where orders_id = ?");
            PreparedStatement itemsStatement = connection.prepareStatement("select * from items where id = ?")
        ) {
            officiantStatement.setInt(1, orderResult.getInt("officiant_id"));
            ResultSet resultOfficiant = officiantStatement.executeQuery();
            dictionaryStatement.setInt(1, orderResult.getInt("id"));
            ResultSet resultDictionary = dictionaryStatement.executeQuery();
            ResultSet resultItems;
            HashMap<Item, Integer> items = new HashMap<>();
            while(resultDictionary.next()){
                int itemID = resultDictionary.getInt("items_dictionary_id");
                int itemQuantity = resultDictionary.getInt("quantity");
                itemsStatement.setInt(1, itemID);
                resultItems = itemsStatement.executeQuery();
                Item item = new Item(resultItems.getString("name"), resultItems.getString("description"), resultItems.getInt("cost"), resultItems.getInt("id"));
                items.put(item, itemQuantity);
            }
            return new Order(new Officiant(resultOfficiant.getString("first_name"), resultOfficiant.getString("second_name"), null, resultOfficiant.getInt("id")),
                    orderResult.getDate("date").toLocalDate(), items, orderResult.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
