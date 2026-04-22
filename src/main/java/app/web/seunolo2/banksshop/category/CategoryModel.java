package app.web.seunolo2.banksshop.category;

import app.web.seunolo2.banksshop.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryModel {
    private Long categoryId;
    private String name;
    private List<Product> products;
}
