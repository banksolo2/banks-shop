package app.web.seunolo2.banksshop.product;

import jakarta.validation.constraints.FutureOrPresent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where lower(p.category.name) = lower(:categoryName)")
    public List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("select p from Product p where lower(p.brand) = lower(:brand)")
    public List<Product> getProductsByBrand(@Param("brand") String brand);

    @Query("select p from Product p where lower(p.category.name) = lower(:categoryName) and lower(p.brand) = lower(:brand)")
    public List<Product> getProductsByCategoryNameAndBrand(@Param("categoryName") String categoryName, @Param("brand") String brand);

    public List<Product> getProductsByNameIgnoreCase(String name);

    public List<Product> getProductsByBrandAndNameIgnoreCase(String brand,String name);

    public Integer countProductsByBrandAndNameIgnoreCase(String brand, String name);
}
