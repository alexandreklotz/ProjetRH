package aubay.lu.projetrh.repository;

import aubay.lu.projetrh.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query("FROM Question q WHERE q.qcm = :qcmid")
    List<Question> findQuestionByQcmId(@Param("qcmid")UUID qcmid);

}
