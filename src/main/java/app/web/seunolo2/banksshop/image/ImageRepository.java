package app.web.seunolo2.banksshop.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {


    @Query("select i from Image i where i.product.productId = :productId")
    public List<Image> getImagesByProductProductId(@Param("productId") Long productId);


}
