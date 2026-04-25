package app.web.seunolo2.banksshop.category;


import app.web.seunolo2.banksshop.exceptions.CategoryNotFoundException;
import app.web.seunolo2.banksshop.responseMessage.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private ResponseMessage rm;

    @GetMapping
    public ResponseEntity<ResponseMessage> getAllCategories(){
        rm = categoryService.getAllCategories();

        return new ResponseEntity<>(rm, rm.getHttpStatus());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ResponseMessage> getCategoryById(@PathVariable Long id){
        rm = categoryService.getCategoryById(id);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

    @GetMapping("/category")
    public ResponseEntity<ResponseMessage> getCategoryByName(@RequestParam("name") String name){
        rm = categoryService.getCategoryByName(name);
        return new ResponseEntity<>(rm, rm.getHttpStatus());
    }

    @PostMapping("/category")
    public ResponseEntity<ResponseMessage> saveCategory(@RequestBody CategoryModel categoryModel){
        rm = categoryService.createCategory(categoryModel);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<ResponseMessage> updateCategory(@PathVariable Long id, @RequestBody CategoryModel categoryModel){
        rm = categoryService.updateCategory(id,categoryModel);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<ResponseMessage> deleteCategoryById(@PathVariable Long id){
        rm = categoryService.deleteCategory(id);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }
}
