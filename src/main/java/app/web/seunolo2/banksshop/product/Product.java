package app.web.seunolo2.banksshop.product;

import app.web.seunolo2.banksshop.category.Category;
import app.web.seunolo2.banksshop.image.Image;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Integer inventory;
    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn( name = "category_id")
    private Category category;
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Image> images;
}
