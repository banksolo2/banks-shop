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
    private ResponseMessage rm;

    @Override
    public ResponseMessage getAllCategories() {
        return ResponseMessage.builder()
                .type("success")
                .message("All categories")
                .object(categoryRepository.findAll())
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseMessage getCategoryById(Long categoryId) {
        try {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            if (Objects.isNull(category))
                throw new CategoryNotFoundException("Category with ID " + categoryId + " not found");

            return ResponseMessage.builder()
                    .type("success")
                    .message("Category found")
                    .object(category)
                    .httpStatus(HttpStatus.FOUND)
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

    @Override
    public ResponseMessage getCategoryByName(String name) {
        try {
            Category category = categoryRepository.getCategoryByNameIgnoreCase(name).orElse(null);
            if (Objects.isNull(category))
                throw new CategoryNotFoundException("Category not found");

            return ResponseMessage.builder()
                    .type("success")
                    .message("Category found")
                    .object(category)
                    .httpStatus(HttpStatus.FOUND)
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
            rm = getCategoryById(categoryId);
            if(rm.getType().equals("error"))
                return rm;

            Category category = (Category) rm.getObject();
            category.setName(categoryModel.getName());

            return ResponseMessage.builder()
                    .type("success")
                    .message("Category updated")
                    .object(categoryRepository.save(category))
                    .httpStatus(HttpStatus.OK)
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
            rm = getCategoryById(categoryId);
            if(rm.getType().equals("error"))
                return rm;

            Category category = (Category) rm.getObject();
            categoryRepository.delete(category);
            return ResponseMessage.builder()
                    .type("success")
                    .message("Category deleted")
                    .httpStatus(HttpStatus.NO_CONTENT)
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
