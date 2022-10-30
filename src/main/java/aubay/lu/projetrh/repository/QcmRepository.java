package aubay.lu.projetrh.repository;

import aubay.lu.projetrh.model.Qcm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QcmRepository extends JpaRepository<Qcm, UUID> {

    @Query("FROM Qcm q WHERE q.titre = :title")
    List<Qcm> findQcmByName(@Param("title")String title);
}
