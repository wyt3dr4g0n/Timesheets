package com.cayuse.timesheets.web.rest;

import com.cayuse.timesheets.TimesheetsApp;

import com.cayuse.timesheets.domain.Time;
import com.cayuse.timesheets.repository.TimeRepository;
import com.cayuse.timesheets.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.cayuse.timesheets.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimeResource REST controller.
 *
 * @see TimeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimesheetsApp.class)
public class TimeResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_COST = 1F;
    private static final Float UPDATED_COST = 2F;

    private static final Float DEFAULT_FOR_BILLING = 1F;
    private static final Float UPDATED_FOR_BILLING = 2F;

    private static final Float DEFAULT_FOR_PAYROLL = 1F;
    private static final Float UPDATED_FOR_PAYROLL = 2F;

    private static final String DEFAULT_BILLING_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENTS_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimeMockMvc;

    private Time time;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeResource timeResource = new TimeResource(timeRepository);
        this.restTimeMockMvc = MockMvcBuilders.standaloneSetup(timeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Time createEntity(EntityManager em) {
        Time time = new Time()
            .date(DEFAULT_DATE)
            .cost(DEFAULT_COST)
            .forBilling(DEFAULT_FOR_BILLING)
            .forPayroll(DEFAULT_FOR_PAYROLL)
            .billingDescription(DEFAULT_BILLING_DESCRIPTION)
            .description(DEFAULT_DESCRIPTION)
            .attachments(DEFAULT_ATTACHMENTS)
            .attachmentsContentType(DEFAULT_ATTACHMENTS_CONTENT_TYPE)
            .notes(DEFAULT_NOTES)
            .createdBy(DEFAULT_CREATED_BY);
        return time;
    }

    @Before
    public void initTest() {
        time = createEntity(em);
    }

    @Test
    @Transactional
    public void createTime() throws Exception {
        int databaseSizeBeforeCreate = timeRepository.findAll().size();

        // Create the Time
        restTimeMockMvc.perform(post("/api/times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(time)))
            .andExpect(status().isCreated());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeCreate + 1);
        Time testTime = timeList.get(timeList.size() - 1);
        assertThat(testTime.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTime.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testTime.getForBilling()).isEqualTo(DEFAULT_FOR_BILLING);
        assertThat(testTime.getForPayroll()).isEqualTo(DEFAULT_FOR_PAYROLL);
        assertThat(testTime.getBillingDescription()).isEqualTo(DEFAULT_BILLING_DESCRIPTION);
        assertThat(testTime.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTime.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testTime.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testTime.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testTime.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createTimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeRepository.findAll().size();

        // Create the Time with an existing ID
        time.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeMockMvc.perform(post("/api/times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(time)))
            .andExpect(status().isBadRequest());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimes() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        // Get all the timeList
        restTimeMockMvc.perform(get("/api/times?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(time.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].forBilling").value(hasItem(DEFAULT_FOR_BILLING.doubleValue())))
            .andExpect(jsonPath("$.[*].forPayroll").value(hasItem(DEFAULT_FOR_PAYROLL.doubleValue())))
            .andExpect(jsonPath("$.[*].billingDescription").value(hasItem(DEFAULT_BILLING_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        // Get the time
        restTimeMockMvc.perform(get("/api/times/{id}", time.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(time.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.forBilling").value(DEFAULT_FOR_BILLING.doubleValue()))
            .andExpect(jsonPath("$.forPayroll").value(DEFAULT_FOR_PAYROLL.doubleValue()))
            .andExpect(jsonPath("$.billingDescription").value(DEFAULT_BILLING_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.attachmentsContentType").value(DEFAULT_ATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachments").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS)))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTime() throws Exception {
        // Get the time
        restTimeMockMvc.perform(get("/api/times/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        int databaseSizeBeforeUpdate = timeRepository.findAll().size();

        // Update the time
        Time updatedTime = timeRepository.findById(time.getId()).get();
        // Disconnect from session so that the updates on updatedTime are not directly saved in db
        em.detach(updatedTime);
        updatedTime
            .date(UPDATED_DATE)
            .cost(UPDATED_COST)
            .forBilling(UPDATED_FOR_BILLING)
            .forPayroll(UPDATED_FOR_PAYROLL)
            .billingDescription(UPDATED_BILLING_DESCRIPTION)
            .description(UPDATED_DESCRIPTION)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .notes(UPDATED_NOTES)
            .createdBy(UPDATED_CREATED_BY);

        restTimeMockMvc.perform(put("/api/times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTime)))
            .andExpect(status().isOk());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
        Time testTime = timeList.get(timeList.size() - 1);
        assertThat(testTime.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTime.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testTime.getForBilling()).isEqualTo(UPDATED_FOR_BILLING);
        assertThat(testTime.getForPayroll()).isEqualTo(UPDATED_FOR_PAYROLL);
        assertThat(testTime.getBillingDescription()).isEqualTo(UPDATED_BILLING_DESCRIPTION);
        assertThat(testTime.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTime.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testTime.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testTime.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testTime.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingTime() throws Exception {
        int databaseSizeBeforeUpdate = timeRepository.findAll().size();

        // Create the Time

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeMockMvc.perform(put("/api/times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(time)))
            .andExpect(status().isBadRequest());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        int databaseSizeBeforeDelete = timeRepository.findAll().size();

        // Get the time
        restTimeMockMvc.perform(delete("/api/times/{id}", time.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Time.class);
        Time time1 = new Time();
        time1.setId(1L);
        Time time2 = new Time();
        time2.setId(time1.getId());
        assertThat(time1).isEqualTo(time2);
        time2.setId(2L);
        assertThat(time1).isNotEqualTo(time2);
        time1.setId(null);
        assertThat(time1).isNotEqualTo(time2);
    }
}
