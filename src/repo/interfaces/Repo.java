package repo.interfaces;

import java.util.List;

public interface Repo<T>  {

     T add(T object);
    T update(T object);
    List<T> getAll();
    void loadAll();

      default T getById(int id){
        throw new UnsupportedOperationException();
    }
    
}
