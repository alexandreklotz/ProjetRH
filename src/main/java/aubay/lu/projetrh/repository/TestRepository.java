package aubay.lu.projetrh.repository;

import aubay.lu.projetrh.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<Test, UUID> {

    @Query("FROM Test t WHERE t.utilisateur = :userid")
    List<Test> findTestByCandidat(@Param("userid")UUID userid);
}
