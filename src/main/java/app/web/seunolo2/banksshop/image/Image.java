package app.web.seunolo2.banksshop.image;

import app.web.seunolo2.banksshop.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String fileName;
    private String fileType;
    @Lob
    private Blob image;
    private String downloadUrl;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
