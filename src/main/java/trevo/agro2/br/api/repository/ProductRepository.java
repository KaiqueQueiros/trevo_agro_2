package trevo.agro2.br.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trevo.agro2.br.api.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByIdIn(List<UUID> products);
    Boolean existsByName(String name);
    Optional<Product> findByName(String name);

}
