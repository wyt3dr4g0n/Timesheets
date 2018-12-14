package com.cayuse.timesheets.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cayuse.timesheets.domain.PayCode;
import com.cayuse.timesheets.repository.PayCodeRepository;
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
 * REST controller for managing PayCode.
 */
@RestController
@RequestMapping("/api")
public class PayCodeResource {

    private final Logger log = LoggerFactory.getLogger(PayCodeResource.class);

    private static final String ENTITY_NAME = "payCode";

    private final PayCodeRepository payCodeRepository;

    public PayCodeResource(PayCodeRepository payCodeRepository) {
        this.payCodeRepository = payCodeRepository;
    }

    /**
     * POST  /pay-codes : Create a new payCode.
     *
     * @param payCode the payCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payCode, or with status 400 (Bad Request) if the payCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pay-codes")
    @Timed
    public ResponseEntity<PayCode> createPayCode(@RequestBody PayCode payCode) throws URISyntaxException {
        log.debug("REST request to save PayCode : {}", payCode);
        if (payCode.getId() != null) {
            throw new BadRequestAlertException("A new payCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayCode result = payCodeRepository.save(payCode);
        return ResponseEntity.created(new URI("/api/pay-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pay-codes : Updates an existing payCode.
     *
     * @param payCode the payCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payCode,
     * or with status 400 (Bad Request) if the payCode is not valid,
     * or with status 500 (Internal Server Error) if the payCode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pay-codes")
    @Timed
    public ResponseEntity<PayCode> updatePayCode(@RequestBody PayCode payCode) throws URISyntaxException {
        log.debug("REST request to update PayCode : {}", payCode);
        if (payCode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PayCode result = payCodeRepository.save(payCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pay-codes : get all the payCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payCodes in body
     */
    @GetMapping("/pay-codes")
    @Timed
    public List<PayCode> getAllPayCodes() {
        log.debug("REST request to get all PayCodes");
        return payCodeRepository.findAll();
    }

    /**
     * GET  /pay-codes/:id : get the "id" payCode.
     *
     * @param id the id of the payCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payCode, or with status 404 (Not Found)
     */
    @GetMapping("/pay-codes/{id}")
    @Timed
    public ResponseEntity<PayCode> getPayCode(@PathVariable Long id) {
        log.debug("REST request to get PayCode : {}", id);
        Optional<PayCode> payCode = payCodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(payCode);
    }

    /**
     * DELETE  /pay-codes/:id : delete the "id" payCode.
     *
     * @param id the id of the payCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pay-codes/{id}")
    @Timed
    public ResponseEntity<Void> deletePayCode(@PathVariable Long id) {
        log.debug("REST request to delete PayCode : {}", id);

        payCodeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
