package uasz.sn.GestionEnseignement.Authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uasz.sn.GestionEnseignement.Authentification.modele.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("SELECT r FROM Role r WHERE r.role = :role")
    Role findRoleByRole(@Param("role") String role);
}
