package app.web.seunolo2.banksshop.category;

import app.web.seunolo2.banksshop.responseMessage.ResponseMessage;

import java.util.List;

public interface CategoryService {

    public List<Category> getAllCategories();
    public Category getCategoryById(Long categoryId);

    public Category getCategoryByName(String name);

    public ResponseMessage createCategory(CategoryModel categoryModel);

    public ResponseMessage updateCategory(Long categoryId, CategoryModel categoryModel);

    public ResponseMessage deleteCategory(Long categoryId);



}
