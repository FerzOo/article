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
        repository.save(e);
    }

    @Override
    public E findById(PK id) {
        return repository.find(id);
    }

    @Override
    public void deleteById(PK id) {
        repository.removeById(id);
    }

    @Override
    public void update(E e) {
        repository.update(e);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }
}
