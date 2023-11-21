package sn.esmt.eapplication.productmicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.eapplication.productmicroservice.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
