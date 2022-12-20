package aubay.lu.projetrh.repository;

import aubay.lu.projetrh.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query("FROM Roles r WHERE r.denomination = :rolename")
    Optional<Roles> findRoleByName(@Param("rolename")String rolename);
}
