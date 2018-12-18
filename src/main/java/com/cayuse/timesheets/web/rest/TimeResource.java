
package com.cayuse.timesheets.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cayuse.timesheets.domain.Time;
import com.cayuse.timesheets.repository.TimeRepository;
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
 * REST controller for managing Time.
 */
@RestController
@RequestMapping("/api")
public class TimeResource {

    private final Logger log = LoggerFactory.getLogger(TimeResource.class);

    private static final String ENTITY_NAME = "time";

    private final TimeRepository timeRepository;

    public TimeResource(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    /**
     * POST  /times : Create a new time.
     *
     * @param time the time to create
     * @return the ResponseEntity with status 201 (Created) and with body the new time, or with status 400 (Bad Request) if the time has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/times")
    @Timed
    public ResponseEntity<Time> createTime(@RequestBody Time time) throws URISyntaxException {
        log.debug("REST request to save Time : {}", time);
        if (time.getId() != null) {
            throw new BadRequestAlertException("A new time cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Time result = timeRepository.save(time);
        return ResponseEntity.created(new URI("/api/times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /times : Updates an existing time.
     *
     * @param time the time to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated time,
     * or with status 400 (Bad Request) if the time is not valid,
     * or with status 500 (Internal Server Error) if the time couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/times")
    @Timed
    public ResponseEntity<Time> updateTime(@RequestBody Time time) throws URISyntaxException {
        log.debug("REST request to update Time : {}", time);
        if (time.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Time result = timeRepository.save(time);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, time.getId().toString()))
            .body(result);
    }

    /**
     * GET  /times : get all the times.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of times in body
     */
    @GetMapping("/times")
    @Timed
    public List<Time> getAllTimes() {
        log.debug("REST request to get all Times");
        return timeRepository.findAll();
    }

    /**
     * GET  /times/:id : get the "id" time.
     *
     * @param id the id of the time to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the time, or with status 404 (Not Found)
     */
    @GetMapping("/times/{id}")
    @Timed
    public ResponseEntity<Time> getTime(@PathVariable Long id) {
        log.debug("REST request to get Time : {}", id);
        Optional<Time> time = timeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(time);
    }

    /**
     * DELETE  /times/:id : delete the "id" time.
     *
     * @param id the id of the time to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/times/{id}")
    @Timed
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        log.debug("REST request to delete Time : {}", id);

        timeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
