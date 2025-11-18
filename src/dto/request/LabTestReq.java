package dto.request;

public class LabTestReq {

    private String oldTestName;
    private String testName;
    private Double fees;

    
    public LabTestReq(String testName, double fees) {
        this.testName = testName;
        this.fees = fees;
    }


    public LabTestReq(String oldTestName, String testName, double fees) {
        this(testName, fees);
        this.oldTestName = oldTestName;
    }

    public String getOldTestName() {
        return oldTestName;
    }

    public String getTestName() {
        return testName;
    }

    public Double getFees() {
        return fees;
    }

    public boolean hasValidName() {
        return testName != null && !testName.trim().isEmpty();
    }

    public boolean hasValidFees() {
        return fees != null && fees >= 0;
    }
}
