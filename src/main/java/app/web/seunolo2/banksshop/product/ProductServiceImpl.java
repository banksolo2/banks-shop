package app.web.seunolo2.banksshop.product;

import app.web.seunolo2.banksshop.category.Category;
import app.web.seunolo2.banksshop.category.CategoryRepository;
import app.web.seunolo2.banksshop.exceptions.ProductNotFoundException;
import app.web.seunolo2.banksshop.responseMessage.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private ResponseMessage rm;
    private String message;
    private HttpStatus httpStatus;

    @Override
    public ResponseMessage addProduct(ProductModel productModel) {
        try {

            Category category = categoryRepository.getCategoryByNameIgnoreCase(
                    productModel.getCategory()
            ).orElse(categoryRepository.save(Category.builder().name(productModel.getCategory()).build()));

            return ResponseMessage.builder()
                    .type("success")
                    .message("Product created")
                    .object(productRepository.save(setCreateProduct(productModel, category)))
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

    private Product setCreateProduct(ProductModel productModel, Category category){
        return Product.builder()
                .name(productModel.getName())
                .brand(productModel.getBrand())
                .description(productModel.getDescription())
                .price(productModel.getPrice())
                .inventory(productModel.getInventory())
                .category(category)
                .build();
    }

    @Override
    public ResponseMessage getById(Long productId) {
        try{
            Product product = productRepository.findById(productId).orElse(null);
            if(Objects.isNull(product))
                throw new ProductNotFoundException("Product with ID "+productId+" not found");

            return ResponseMessage.builder()
                    .type("success")
                    .message("Product found")
                    .object(product)
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        catch(ProductNotFoundException productNotFoundException){
            return ResponseMessage.builder()
                    .type("error")
                    .message(productNotFoundException.getMessage())
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
    public ResponseMessage getAllProduct() {
        try {
            return ResponseMessage.builder()
                    .type("success")
                    .message("All products")
                    .object(productRepository.findAll())
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
    public ResponseMessage updateProduct(Long productId, ProductModel productModel) {
        try {
            rm = getById(productId);
            if(rm.getType().equals("error"))
                return rm;

            Product product = (Product) rm.getObject();
            Category category = categoryRepository.getCategoryByNameIgnoreCase(productModel.getCategory())
                    .orElse(categoryRepository.save(Category.builder().name(productModel.getCategory()).build()));

            return ResponseMessage.builder()
                    .type("success")
                    .message("Product updated")
                    .object(productRepository.save(setUpdateProduct(product,productModel,category)))
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

    private Product setUpdateProduct(Product product, ProductModel productModel, Category category){
        product.setBrand(productModel.getBrand());
        product.setCategory(category);
        product.setName(productModel.getName());
        product.setInventory(productModel.getInventory());
        product.setDescription(productModel.getDescription());
        product.setPrice(productModel.getPrice());

        return product;
    }

    @Override
    public ResponseMessage deleteProduct(Long productId) {
       try{
           rm = getById(productId);
           if(rm.getType().equals("error"))
               return rm;
           Product product = (Product) rm.getObject();
           productRepository.delete(product);
           return ResponseMessage.builder()
                   .type("success")
                   .message("Product deleted")
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

    @Override
    public ResponseMessage getProductsByCategoryName(String categoryName) {
        try {
            message = "Products Retrieved";
            var products = productRepository.findByCategoryName(categoryName);
            httpStatus = HttpStatus.OK;
            if(products.isEmpty()) {
                message = "Product not found with category name";
                httpStatus = HttpStatus.NOT_FOUND;
            }


            return ResponseMessage.builder()
                    .type("success")
                    .message(message)
                    .object(products)
                    .httpStatus(httpStatus)
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
    public ResponseMessage getProductsByBrand(String brand) {
        try {
            var products = productRepository.getProductsByBrand(brand);
            message = "Products found";
            httpStatus = HttpStatus.OK;
            if(products.isEmpty()){
                message = "No product found";
                httpStatus = HttpStatus.NOT_FOUND;
            }
            return ResponseMessage.builder()
                    .type("success")
                    .message(message)
                    .object(products)
                    .httpStatus(httpStatus)
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
    public ResponseMessage getProductsByCategoryNameAndBrand(String categoryName, String brand) {
        try {
            var products = productRepository.getProductsByCategoryNameAndBrand(categoryName, brand);
            message = "Products found";
            httpStatus = HttpStatus.OK;
            if (products.isEmpty()) {
                message = "No product found";
                httpStatus = HttpStatus.NOT_FOUND;
            }
            return ResponseMessage.builder()
                    .type("success")
                    .message(message)
                    .object(products)
                    .httpStatus(httpStatus)
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
    public ResponseMessage getProductsByName(String name) {
        try {
            List<Product> products = productRepository.getProductsByNameIgnoreCase(name);
            message = "Products found";
            httpStatus = HttpStatus.OK;
            if(products.isEmpty()){
                message = "No product not found";
                httpStatus = HttpStatus.NOT_FOUND;
            }
            return ResponseMessage.builder()
                    .type("success")
                    .message(message)
                    .object(products)
                    .httpStatus(httpStatus)
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
    public ResponseMessage getProductsByBrandAndName(String brand, String name) {
        try {
            var products = productRepository.getProductsByBrandAndNameIgnoreCase(brand, name);
            message = "Products retrieved";
            httpStatus = HttpStatus.OK;
            if(products.isEmpty()) {
                message = "No product found with brand and name";
                httpStatus = HttpStatus.NOT_FOUND;
            }

            return ResponseMessage.builder()
                    .type("success")
                    .message(message)
                    .object(products)
                    .httpStatus(httpStatus)
                    .build();
        }
        catch (Exception ex){
            return ResponseMessage.builder()
                    .type("error")
                    .message(ex.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseMessage countProductsByBrandAndName(String brand, String name) {
        try {
            var productsCount = productRepository.countProductsByBrandAndNameIgnoreCase(brand, name);
            return ResponseMessage.builder()
                    .type("success")
                    .message("products count retrieved")
                    .object(productsCount)
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
}
