package app.web.seunolo2.banksshop.product;

import app.web.seunolo2.banksshop.category.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductModel {
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Integer inventory;
    private Category category;
}
