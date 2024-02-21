package kth.iv1201.gohire.repository;

import kth.iv1201.gohire.entity.ApplicationStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository responsible for accessing data related to <code>ApplicationStatusEntity</code>s.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatusEntity, Integer> {


}
