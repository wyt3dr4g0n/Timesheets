package com.cayuse.timesheets.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cayuse.timesheets.domain.SubCostCategory;
import com.cayuse.timesheets.repository.SubCostCategoryRepository;
import com.cayuse.timesheets.web.rest.errors.BadRequestAlertException;
import com.cayuse.timesheets.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SubCostCategory.
 */
@RestController
@RequestMapping("/api")
public class SubCostCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SubCostCategoryResource.class);

    private static final String ENTITY_NAME = "subCostCategory";

    private final SubCostCategoryRepository subCostCategoryRepository;

    public SubCostCategoryResource(SubCostCategoryRepository subCostCategoryRepository) {
        this.subCostCategoryRepository = subCostCategoryRepository;
    }

    /**
     * POST  /sub-cost-categories : Create a new subCostCategory.
     *
     * @param subCostCategory the subCostCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subCostCategory, or with status 400 (Bad Request) if the subCostCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sub-cost-categories")
    @Timed
    public ResponseEntity<SubCostCategory> createSubCostCategory(@RequestBody SubCostCategory subCostCategory) throws URISyntaxException {
        log.debug("REST request to save SubCostCategory : {}", subCostCategory);
        if (subCostCategory.getId() != null) {
            throw new BadRequestAlertException("A new subCostCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubCostCategory result = subCostCategoryRepository.save(subCostCategory);
        return ResponseEntity.created(new URI("/api/sub-cost-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sub-cost-categories : Updates an existing subCostCategory.
     *
     * @param subCostCategory the subCostCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subCostCategory,
     * or with status 400 (Bad Request) if the subCostCategory is not valid,
     * or with status 500 (Internal Server Error) if the subCostCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sub-cost-categories")
    @Timed
    public ResponseEntity<SubCostCategory> updateSubCostCategory(@RequestBody SubCostCategory subCostCategory) throws URISyntaxException {
        log.debug("REST request to update SubCostCategory : {}", subCostCategory);
        if (subCostCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubCostCategory result = subCostCategoryRepository.save(subCostCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subCostCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sub-cost-categories : get all the subCostCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subCostCategories in body
     */
    @GetMapping("/sub-cost-categories")
    @Timed
    public List<SubCostCategory> getAllSubCostCategories() {
        log.debug("REST request to get all SubCostCategories");
        return subCostCategoryRepository.findAll();
    }

    /**
     * GET  /sub-cost-categories/:id : get the "id" subCostCategory.
     *
     * @param id the id of the subCostCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subCostCategory, or with status 404 (Not Found)
     */
    @GetMapping("/sub-cost-categories/{id}")
    @Timed
    public ResponseEntity<SubCostCategory> getSubCostCategory(@PathVariable Long id) {
        log.debug("REST request to get SubCostCategory : {}", id);
        Optional<SubCostCategory> subCostCategory = subCostCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subCostCategory);
    }

    /**
     * DELETE  /sub-cost-categories/:id : delete the "id" subCostCategory.
     *
     * @param id the id of the subCostCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sub-cost-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubCostCategory(@PathVariable Long id) {
        log.debug("REST request to delete SubCostCategory : {}", id);

        subCostCategoryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
