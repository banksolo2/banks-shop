package app.web.seunolo2.banksshop.product;

import app.web.seunolo2.banksshop.responseMessage.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private ResponseMessage rm;


    @GetMapping
    public ResponseEntity<ResponseMessage> getAllProducts(){
        rm = productService.getAllProduct();
        return new ResponseEntity<>(rm, rm.getHttpStatus());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseMessage> getProductById(@PathVariable Long id){
        rm = productService.getById(id);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

    @PostMapping("/product")
    public ResponseEntity<ResponseMessage> addProduct(@RequestBody ProductModel productModel){
        rm = productService.addProduct(productModel);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ResponseMessage> updateProduct(@PathVariable Long id, @RequestBody ProductModel productModel){
        rm = productService.updateProduct(id,productModel);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ResponseMessage> deleteProduct(@PathVariable Long id){
        rm = productService.deleteProduct(id);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

    @GetMapping("/category")
    public ResponseEntity<ResponseMessage> getProductsByCategoryName(@RequestParam("category") String category){
        rm = productService.getProductsByCategoryName(category);
        return new ResponseEntity<>(rm, rm.getHttpStatus());
    }

    @GetMapping("/brand-and-name")
    public ResponseEntity<ResponseMessage> getProductByBrandAndName(@RequestParam("name") String name, @RequestParam("brand") String brand){
        rm = productService.getProductsByBrandAndName(brand,name);
        return new ResponseEntity<>(rm, rm.getHttpStatus());
    }

    @GetMapping("/category-and-brand")
    public ResponseEntity<ResponseMessage> getProductByCategoryAndBrand(@RequestParam("category")String category, @RequestParam("brand") String brand){
        rm = productService.getProductsByCategoryNameAndBrand(category,brand);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }


    @GetMapping("/brand")
    public ResponseEntity<ResponseMessage> getProductsByBrand(@RequestParam("brand") String brand){
        rm = productService.getProductsByBrand(brand);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

    @GetMapping("/name")
    public ResponseEntity<ResponseMessage> getProductsByName(@RequestParam("name") String name){
        rm = productService.getProductsByName(name);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

    @GetMapping("/count/brand-and-name")
    public ResponseEntity<ResponseMessage> getProductsCountByBrandAndName(@RequestParam("brand")String name, @RequestParam("brand")String brand){
        rm = productService.countProductsByBrandAndName(brand,name);
        return new ResponseEntity<>(rm,rm.getHttpStatus());
    }

}
