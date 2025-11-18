package repo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConfig.DBConnection;
import exceptions.CRUDFailedException;
import exceptions.NotFoundException;
import models.Role;
import repo.interfaces.Repo;
import repo.query.RoleQuery;

public class RoleRepo implements Repo<Role> {

    private static RoleRepo instance;

    private RoleRepo(){}

    public static RoleRepo getRoleRepo(){

        if(instance == null){
            instance = new RoleRepo();
        }

        return instance;
    }

      private final Map<Integer , Role> roles = new HashMap<>();

      private Role setRoleDetails(ResultSet rs) throws SQLException{

            return new Role( 
                rs.getInt("id"),
                rs.getString("rolename") 
                );
      }

      private void makePrepareStatementDetails(PreparedStatement ps , Role role) throws SQLException{

            ps.setString(1, role.getRoleName());
      }

    @Override
    public void loadAll(){

        try(PreparedStatement ps = DBConnection.getInstance().
            getConnection().prepareStatement(RoleQuery.LOADALL.getQuery())){

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Role role = setRoleDetails(rs);
                    roles.put(role.getId(), role);
                }
            }
            catch(SQLException e){
            System.out.println("Unable to Load all the Role data...");
                

            }

    }

    @Override
    public Role add(Role role){

        int id = addToDB(role);
        role.setId(id);
        roles.put(id, role);
        return role;
    }

    @Override 
    public Role update(Role role){

        updateToDB(role);
        roles.put(role.getId(), role);
        return role;
    }


    @Override 
    public List<Role> getAll(){
        return new ArrayList<>(roles.values());
    }

    @Override
    public Role getById(int id){
        
        return roles.get(id);
    }

    public String   getRoleNameById(int id){

        Role role = getById(id);

        if(role != null){
            return role.getRoleName();
        }

        throw new NotFoundException("The Role was Not Found...");

    }

    private int addToDB(Role role){

        try(PreparedStatement ps = DBConnection.getInstance().getConnection()
        .prepareStatement(RoleQuery.INSERT.getQuery())){

            makePrepareStatementDetails(ps, role);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("id");
            }
            else{
                throw new CRUDFailedException("No ID was retirned...");
            }
        }
        catch(SQLException e){
            throw new CRUDFailedException("Role Inseration was failed..." );
        }

    }

    private void updateToDB(Role role){

        try(PreparedStatement ps = DBConnection.getInstance().getConnection()
        .prepareStatement(RoleQuery.UPDATE.getQuery())){

            makePrepareStatementDetails(ps, role);
            ps.setInt(2, role.getId());
            ps.executeUpdate();

        }
        catch(SQLException e){
            throw new CRUDFailedException("Role Updation was Failed..." );

        }
    }


    
}
