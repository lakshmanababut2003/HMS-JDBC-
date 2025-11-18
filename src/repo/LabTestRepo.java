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
import models.LabTest;
import repo.interfaces.Repo;
import repo.query.LabTestQuery;

public class LabTestRepo implements Repo<LabTest> {

    private static LabTestRepo instance;

    private LabTestRepo(){}

    public static LabTestRepo getLabTestRepo(){
        if(instance == null){
            instance = new LabTestRepo();
        }
        return instance;
    }

    private final Map<Integer, LabTest> tests = new HashMap<>();

    private LabTest setLabTestDetails(ResultSet rs) throws SQLException {
        return new LabTest(
            rs.getInt("id"),
            rs.getString("test_name"),
            rs.getDouble("fees")
        );
    }

    private void makePrepareStatementDetails(PreparedStatement ps, LabTest test) throws SQLException {
        ps.setString(1, test.getTestName());
        ps.setDouble(2, test.getFees());
    }

    @Override
    public void loadAll() {

        try(PreparedStatement ps = DBConnection.getInstance().getConnection()
            .prepareStatement(LabTestQuery.LOADALL.getQuery())) {

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                LabTest test = setLabTestDetails(rs);
                tests.put(test.getId(), test);
            }

        } 
        catch(SQLException e) {
            System.out.println("Unable to Load all the LabTest data...");
        }
    }

    @Override
    public LabTest add(LabTest test) {

        int id = addToDB(test);
        test.setId(id);
        tests.put(id, test);
        return test;
    }

    @Override
    public LabTest update(LabTest test) {

        updateToDB(test);
        tests.put(test.getId(), test);
        return test;
    }

    @Override
    public List<LabTest> getAll(){
        return new ArrayList<>(tests.values());
    }

    @Override
    public LabTest getById(int id) {
        return tests.get(id);
    }

    private int addToDB(LabTest test){

        try(PreparedStatement ps = DBConnection.getInstance().getConnection()
            .prepareStatement(LabTestQuery.INSERT.getQuery())) {

            makePrepareStatementDetails(ps, test);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt("id");
            }

            throw new CRUDFailedException("No ID was returned...");

        } catch(SQLException e){
            throw new CRUDFailedException("LabTest Insertion Failed...");
        }
    }

    private void updateToDB(LabTest test){

        try(PreparedStatement ps = DBConnection.getInstance().getConnection()
            .prepareStatement(LabTestQuery.UPDATE.getQuery())) {

            makePrepareStatementDetails(ps, test);
            ps.setInt(3, test.getId());
            ps.executeUpdate();

        } catch(SQLException e){
            throw new CRUDFailedException("LabTest Updation Failed...");
        }
    }

    public LabTest getLabTestByName(String name){

        for(LabTest t : tests.values()){
            if(t.getTestName().equalsIgnoreCase(name)){
                return t;
            }
        }

        throw new NotFoundException("LabTest Not Found...");
    }
}
