package aubay.lu.projetrh.repository;

import aubay.lu.projetrh.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {

    @Query("FROM Utilisateur u WHERE u.userLogin = :userlogin")
    Optional<Utilisateur> findUserWithLogin(@Param("userlogin")String userlogin);

    @Query("FROM Utilisateur u JOIN FETCH u.role WHERE u.userLogin = :userlogin")
    Optional<Utilisateur> findUserWithRoles(@Param("userlogin")String userlogin);

    @Query("FROM Utilisateur u WHERE u.mailAddress = :mailAddress")
    Optional<Utilisateur> findIfUsedMailAddress(@Param("mailAddress")String mailAddress);

}
