package ir.maktab39.base.repository;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface BaseRepository<PK extends Serializable, E> {

    Class<E> getEntityClass();

    void startTransaction();

    void commit();

    void rollback();

    void save(E e);

    E find(PK id);

    void removeById(PK id);

    void update(E e);

    List<E> findAll();

    List<E> findAll(Predicate<E> predicate);

    List findAll(Function<E, ?> function);
}
