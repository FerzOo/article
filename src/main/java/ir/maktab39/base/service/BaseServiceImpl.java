package ir.maktab39.base.service;

import ir.maktab39.ComponentFactory;
import ir.maktab39.ErrorHandler;
import ir.maktab39.base.repository.BaseRepository;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class BaseServiceImpl
        <PK extends Serializable, E, Repository extends BaseRepository<PK, E>>
        implements BaseService<E, PK> {


    protected Repository repository;

    public BaseServiceImpl(Class<? extends Repository> repositoryClass) {
        try {
            this.repository = (Repository) ComponentFactory.
                    getSingletonObject(repositoryClass);
        } catch (Exception e) {
            ErrorHandler.showMessage(e);
        }
    }

    @Override
    public void save(E e) {
        repository.startTransaction();
        repository.save(e);
        repository.commit();
    }

    @Override
    public E findById(PK id) {
        return repository.find(id);
    }

    @Override
    public void deleteById(PK id) {
        repository.startTransaction();
        repository.removeById(id);
        repository.commit();
    }

    @Override
    public void update(E e) {
        repository.startTransaction();
        repository.update(e);
        repository.commit();
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }
}
