package data.servlets;

import com.google.gson.Gson;
import data.storage.DAOFactory;
import data.storage.SqlPerRequestDAOFactory;
import resources.model.ItemsDAO;
import resources.model.dao.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/getitems")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DAOFactory daoFactory = SqlPerRequestDAOFactory.getInstance();
        ItemsDAO itemsDAO = daoFactory.getItemsDAO();
        ArrayList<Item> items = (ArrayList<Item>) itemsDAO.getAll();
        if(req.getParameter("id") != null){
            ArrayList<Item> temp = new ArrayList<>();
            int id = Integer.parseInt(req.getParameter("id"));
            for(int i = 0; i < items.size(); i++){
                if(items.get(i).getId() == id)
                    temp.add(items.get(i));
            }
            items = temp;
        }

        if(req.getParameter("name") != null){
            ArrayList<Item> temp = new ArrayList<>();
            String name = req.getParameter("name");
            for(int i = 0; i < items.size(); i++){
                if(items.get(i).getName().equals(name))
                    temp.add(items.get(i));
            }
            items = temp;
        }

        if(req.getParameter("description") != null){
            ArrayList<Item> temp = new ArrayList<>();
            String description = req.getParameter("description");
            for(int i = 0; i < items.size(); i++){
                if(items.get(i).getDescription().equals(description))
                    temp.add(items.get(i));
            }
            items = temp;
        }

        if(req.getParameter("cost") != null){
            ArrayList<Item> temp = new ArrayList<>();
            int cost = Integer.parseInt(req.getParameter("cost"));
            for(int i = 0; i < items.size(); i++){
                if(items.get(i).getCost() == cost)
                    temp.add(items.get(i));
            }
            items = temp;
        }

        Gson gson = new Gson();
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(items));
        out.flush();
    }
}
