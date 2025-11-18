package exceptions;

public class NomoreRecordsException  extends RuntimeException{

    public NomoreRecordsException(String msg){
        super(msg);
    }
}
