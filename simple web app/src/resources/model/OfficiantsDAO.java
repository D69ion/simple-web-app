package resources.model;

import resources.model.dao.Officiant;

import java.util.Collection;

public interface OfficiantsDAO {
    public boolean insert(Officiant officiant);

    public boolean delete(Officiant officiant);

    public Officiant findByID(int id);

    public boolean update(Officiant officiant);

    public boolean saveOrUpdate(Officiant o);

    public Collection<Officiant> findByName(String firstName, String secondName);
}
