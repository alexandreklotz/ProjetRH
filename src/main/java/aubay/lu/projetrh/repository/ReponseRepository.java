package aubay.lu.projetrh.repository;

import aubay.lu.projetrh.model.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReponseRepository extends JpaRepository<Reponse, UUID> {
}
