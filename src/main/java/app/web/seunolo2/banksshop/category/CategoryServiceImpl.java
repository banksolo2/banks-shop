package app.web.seunolo2.banksshop.category;

import app.web.seunolo2.banksshop.exceptions.CategoryNotFoundException;
import app.web.seunolo2.banksshop.responseMessage.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException("Category with ID "+categoryId+" not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.getCategoryByNameIgnoreCase(name)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public ResponseMessage createCategory(CategoryModel categoryModel) {
        try {
            Boolean isCategoryNameExist = categoryRepository.existsCategoryByNameIgnoreCase(categoryModel.getName());
            if(isCategoryNameExist)
                return ResponseMessage.builder()
                        .type("error")
                        .message("Category name already exist")
                        .httpStatus(HttpStatus.CONFLICT)
                        .build();


            var category = Category.builder()
                    .name(categoryModel.getName())
                    .build();
            return ResponseMessage.builder()
                    .type("success")
                    .message("Category created")
                    .object(categoryRepository.save(category))
                    .httpStatus(HttpStatus.CREATED)
                    .build();
        }
        catch(Exception ex){
            return ResponseMessage.builder()
                    .type("error")
                    .message(ex.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseMessage updateCategory(Long categoryId, CategoryModel categoryModel) {
        try {
            Category category = getCategoryById(categoryId);
            category.setName(categoryModel.getName());

            return ResponseMessage.builder()
                    .type("success")
                    .message("Category updated")
                    .object(categoryRepository.save(category))
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        catch(CategoryNotFoundException categoryNotFoundException){
            return  ResponseMessage.builder()
                    .type("error")
                    .message(categoryNotFoundException.getMessage())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        catch(Exception ex){
            return ResponseMessage.builder()
                    .type("error")
                    .message(ex.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseMessage deleteCategory(Long categoryId) {
        try {
            Category category = getCategoryById(categoryId);
            categoryRepository.deleteById(categoryId);
            return ResponseMessage.builder()
                    .type("success")
                    .message("Category deleted")
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .build();
        }
        catch(CategoryNotFoundException categoryNotFoundException){
            return ResponseMessage.builder()
                    .type("error")
                    .message(categoryNotFoundException.getMessage())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        catch(Exception ex){
            return ResponseMessage.builder()
                    .type("error")
                    .message(ex.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
