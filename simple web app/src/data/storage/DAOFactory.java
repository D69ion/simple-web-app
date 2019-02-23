package data.storage;

import resources.model.ItemsDAO;
import resources.model.OfficiantsDAO;
import resources.model.OrdersDAO;

public abstract class DAOFactory {
    private static DAOFactory instance;

    protected DAOFactory() {

    }

    public static DAOFactory getInstance() {
        if(instance == null){
            instance = new SqlPerRequestDAOFactory();
        }
        return instance;
    }

    public abstract OrdersDAO getOrdersDAO();

    public abstract OfficiantsDAO getOfficiantsDAO();

    public abstract ItemsDAO getItemsDAO();
}
