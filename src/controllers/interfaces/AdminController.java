package controllers.interfaces;

import java.util.List;

public interface AdminController<T , V> {
    
    V add(T object);
    V update(T object);
    List<V> viewAll();
    default V getByName(String input){
        return null;
    }

    default V getById(int id){
        return null;
    }
}
