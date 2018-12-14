package com.cayuse.timesheets.repository;

import com.cayuse.timesheets.domain.Time;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Time entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

}
