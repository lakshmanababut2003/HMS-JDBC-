package dto.response;

public class LabTestRes {

    private String testName;
    private double fees;

    public LabTestRes(String testName, double fees) {
    
        this.testName = testName;
        this.fees = fees;
    }

    public String getTestName() {
        return testName;
    }

    public double getFees() {
        return fees;
    }
}
