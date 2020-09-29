package ir.maktab39.repositories.category;

import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.entities.Category;

import javax.persistence.EntityManager;

public class CategoryRepoImpl extends BaseRepositoryImpl<Long, Category> implements CategoryRepo {

    @Override
    public Class<Category> getEntityClass() {
        return Category.class;
    }
}
