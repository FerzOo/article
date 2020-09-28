package ir.maktab39.services.category;

import ir.maktab39.base.service.BaseServiceImpl;
import ir.maktab39.entities.Category;
import ir.maktab39.repositories.category.CategoryRepo;
import ir.maktab39.repositories.category.CategoryRepoImpl;

public class CategoryServiceImpl
        extends BaseServiceImpl<Long, Category, CategoryRepo> implements CategoryService {
    public CategoryServiceImpl() {
        super(CategoryRepoImpl.class, Category.class);
    }
}
