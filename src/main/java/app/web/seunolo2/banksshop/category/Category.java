package app.web.seunolo2.banksshop.category;

import app.web.seunolo2.banksshop.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
