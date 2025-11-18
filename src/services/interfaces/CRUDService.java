package services.interfaces;

import java.util.List;

public interface CRUDService<T , V> {

    T add(V object);
    T update(V object);
    List<T> getAll();

      default T getById(int id){
        throw new UnsupportedOperationException();
    }
   
    
}
