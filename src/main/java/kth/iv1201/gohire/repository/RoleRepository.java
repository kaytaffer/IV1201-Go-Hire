package kth.iv1201.gohire.repository;

import kth.iv1201.gohire.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository responsible for accessing data related to <code>RoleEntity</code>s.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findRoleById(Integer id);
}

