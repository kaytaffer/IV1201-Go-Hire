package kth.iv1201.gohire.repository;

import kth.iv1201.gohire.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository responsible for accessing data related to <code>RoleEntity</code>s.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findRoleById(Integer id);
}

