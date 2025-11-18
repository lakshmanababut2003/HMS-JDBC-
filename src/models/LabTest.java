package models;

public class LabTest {

    private int id;
    private String testName;
    private double fees;

    public LabTest(String testName , double fees){

        this.testName = testName;
        this.fees = fees;
    }

    public LabTest(int id , String testName , double fees){
        this(testName, fees);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    
    
}
