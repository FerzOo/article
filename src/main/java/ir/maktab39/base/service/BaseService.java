package ir.maktab39.base.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService <E, PK extends Serializable>{

    void save(E e);

    E findById(PK id);

    void deleteById(PK id);

    void update(E e);

    List<E> findAll();

}
