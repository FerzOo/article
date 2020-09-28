package ir.maktab39.repositories.category;

import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.entities.Category;

import javax.persistence.EntityManager;

public class CategoryRepoImpl extends BaseRepositoryImpl<Long, Category> implements CategoryRepo {

    public CategoryRepoImpl(Class<Category> entityClass) {
        super(entityClass);
    }
}
