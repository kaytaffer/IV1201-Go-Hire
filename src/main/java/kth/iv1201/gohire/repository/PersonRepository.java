package kth.iv1201.gohire.repository;

import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository responsible for accessing data related to <code>PersonEntity</code>s.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
    PersonEntity findPersonById(Integer id);
    PersonEntity findByUsername(String username);
    boolean existsByUsername(String username);
    List<PersonEntity> findPersonEntitiesByRoleIs(RoleEntity role);
}
