package app.web.seunolo2.banksshop.product;

import app.web.seunolo2.banksshop.responseMessage.ResponseMessage;

import java.util.List;

public interface ProductService {
    public ResponseMessage addProduct(ProductModel productModel);

    public ResponseMessage getById(Long productId);

    public ResponseMessage getAllProduct();

    public ResponseMessage updateProduct(Long productId, ProductModel productModel);

    public ResponseMessage deleteProduct(Long productId);

    public ResponseMessage getProductsByCategoryName(String categoryName);

    public ResponseMessage getProductsByBrand(String brand);

    public ResponseMessage getProductsByCategoryNameAndBrand(String categoryName, String brand);

    public ResponseMessage getProductsByName(String name);

    public ResponseMessage getProductsByBrandAndName(String brand, String name);

    public ResponseMessage countProductsByBrandAndName(String brand, String name);
}
