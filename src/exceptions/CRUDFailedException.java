package exceptions;

public class CRUDFailedException extends RuntimeException {

    public CRUDFailedException(String msg){
        super(msg);
    }
    
}
