package resources.model.dao;

import data.storage.DataSourceFactory;
import resources.model.OfficiantsDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class OfficiantsDAOImpl implements OfficiantsDAO {
    private static DataSource dataSource;

    public OfficiantsDAOImpl(){
        dataSource = DataSourceFactory.createDataSource();
    }

    @Override
    public boolean insert(Officiant officiant) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into officiants values (?,?,?)")) {
            preparedStatement.setInt(1, officiant.getId());
            preparedStatement.setString(2, officiant.getFirstName());
            preparedStatement.setString(3, officiant.getSecondName());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Officiant officiant) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from officiants where id = ?")) {
            preparedStatement.setInt(1, officiant.getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Officiant findByID(int id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from officiants where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if(!result.next()){
                return null;
            }
            Officiant officiant = new Officiant(result.getString("first_name"), result.getString("second_name"), /*hashset of orders*/ null, result.getInt("id"));
            return officiant;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Officiant officiant) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update officiants set first_name = ?, second_name = ? where id = ?")) {
            preparedStatement.setString(1, officiant.getFirstName());
            preparedStatement.setString(2, officiant.getSecondName());
            preparedStatement.setInt(3, officiant.getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveOrUpdate(Officiant o) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from officiants where id = ?")) {
            preparedStatement.setInt(1, o.getId());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return update(o);
            }
            else{
                return insert(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Officiant> findByName(String firstName, String secondName) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from officiants where first_name = ? and second_name = ?")) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, secondName);
            ResultSet result = preparedStatement.executeQuery();
            ArrayList<Officiant> officiants = new ArrayList<>();
            while(result.next()){
                officiants.add(new Officiant(result.getString("first_name"), result.getString("second_name"), /*hashset of orders*/ null, result.getInt("id")));
            }
            return officiants;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
