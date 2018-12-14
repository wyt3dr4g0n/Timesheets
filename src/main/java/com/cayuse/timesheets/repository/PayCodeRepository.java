package com.cayuse.timesheets.repository;

import com.cayuse.timesheets.domain.PayCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PayCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayCodeRepository extends JpaRepository<PayCode, Long> {

}
