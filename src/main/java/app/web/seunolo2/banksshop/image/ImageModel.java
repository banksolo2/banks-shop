package app.web.seunolo2.banksshop.image;

import app.web.seunolo2.banksshop.product.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageModel {
    private Long imageId;
    private String downloadUrl;

    private Long productId;
}
