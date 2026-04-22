package app.web.seunolo2.banksshop.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> getCategoryByNameIgnoreCase(String name);

    public Boolean existsCategoryByNameIgnoreCase(String name);
}
