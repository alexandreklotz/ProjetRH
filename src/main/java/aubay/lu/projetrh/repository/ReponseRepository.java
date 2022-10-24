package aubay.lu.projetrh.repository;

import aubay.lu.projetrh.model.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReponseRepository extends JpaRepository<Reponse, UUID> {

    @Query("FROM Reponse r WHERE r.question = :questionId")
    List<Reponse> findReponsesByQuestion(@Param("questionId")UUID questionId);
}
