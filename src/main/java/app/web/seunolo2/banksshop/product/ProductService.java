package app.web.seunolo2.banksshop.product;

import app.web.seunolo2.banksshop.responseMessage.ResponseMessage;

import java.util.List;

public interface ProductService {
    public ResponseMessage addProduct(ProductModel productModel);

    public Product getById(Long productId);

    public List<Product> getAllProduct();

    public ResponseMessage updateProduct(Long productId, ProductModel productModel);

    public ResponseMessage deleteProduct(Long productId);

    public List<Product> getProductsByCategoryName(String categoryName);

    public List<Product> getProductsByBrand(String brand);

    public List<Product> getProductsByCategoryNameAndBrand(String categoryName, String brand);

    public List<Product> getProductsByName(String name);

    public List<Product> getProductsByBrandAndName(String brand, String name);

    public Integer countProductsByBrandAndName(String brand, String name);
}
