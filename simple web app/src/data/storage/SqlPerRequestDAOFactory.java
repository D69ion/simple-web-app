package data.storage;

import resources.model.ItemsDAO;
import resources.model.OfficiantsDAO;
import resources.model.OrdersDAO;
import resources.model.dao.*;

public class SqlPerRequestDAOFactory extends DAOFactory{
    @Override
    public OrdersDAO getOrdersDAO() {
        return new OrdersDAOImpl();
    }

    @Override
    public OfficiantsDAO getOfficiantsDAO() {
        return new OfficiantsDAOImpl();
    }

    @Override
    public ItemsDAO getItemsDAO() {
        return new ItemsDAOImpl();
    }
}
