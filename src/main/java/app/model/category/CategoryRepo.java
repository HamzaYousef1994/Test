package app.model.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {


    List<Category> findAllByOrderByCategoryOrder();

    @Query("select c.nameEn from Category c")
    List<String> categoryNames();
}
