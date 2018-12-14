package com.cayuse.timesheets.repository;

import com.cayuse.timesheets.domain.SubCostCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SubCostCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubCostCategoryRepository extends JpaRepository<SubCostCategory, Long> {

}
