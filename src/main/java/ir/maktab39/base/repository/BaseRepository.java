package ir.maktab39.base.repository;

import java.io.Serializable;
import java.util.List;

public interface BaseRepository<PK extends Serializable, E> {

    void startTransaction();

    void commit();

    void rollback();

    void save(E e);

    E find(PK id);

    void removeById(PK id);

    void update(E e);

    List<E> findAll();
}
