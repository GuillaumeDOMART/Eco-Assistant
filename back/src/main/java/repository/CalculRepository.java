package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculRepository extends JpaRepository<CalculRepository, Long> {
}
