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

    @Override
    public ResponseMessage addProduct(ProductModel productModel) {
        try {

            Category category = categoryRepository.getCategoryByNameIgnoreCase(
                    productModel.getCategory().getName()
            ).orElse(categoryRepository.save(productModel.getCategory()));

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
    public Product getById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if(Objects.isNull(product))
            throw new ProductNotFoundException("Product with ID "+productId+" not found");


        return product;
    }



    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ResponseMessage updateProduct(Long productId, ProductModel productModel) {
        try {
            Product product = getById(productId);
            Category category = categoryRepository.getCategoryByNameIgnoreCase(productModel.getCategory().getName())
                    .orElse(categoryRepository.save(productModel.getCategory()));

            return ResponseMessage.builder()
                    .type("success")
                    .message("Product updated")
                    .object(productRepository.save(setUpdateProduct(product,productModel,category)))
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        catch (ProductNotFoundException productNotFoundException){
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
           Product product = getById(productId);
           productRepository.deleteById(productId);
           return ResponseMessage.builder()
                   .type("success")
                   .message("Product deleted")
                   .httpStatus(HttpStatus.NO_CONTENT)
                   .build();
       }
       catch (ProductNotFoundException productNotFoundException){
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
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.getProductsByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryNameAndBrand(String categoryName, String brand) {
        return productRepository.getProductsByCategoryNameAndBrand(categoryName,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.getProductsByNameIgnoreCase(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.getProductsByBrandAndNameIgnoreCase(brand,name);
    }

    @Override
    public Integer countProductsByBrandAndName(String brand, String name) {
        return productRepository.countProductsByBrandAndNameIgnoreCase(brand,name);
    }
}
