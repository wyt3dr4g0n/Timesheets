package com.cayuse.timesheets.web.rest;

import com.cayuse.timesheets.TimesheetsApp;

import com.cayuse.timesheets.domain.PayCode;
import com.cayuse.timesheets.repository.PayCodeRepository;
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

import javax.persistence.EntityManager;
import java.util.List;


import static com.cayuse.timesheets.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PayCodeResource REST controller.
 *
 * @see PayCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimesheetsApp.class)
public class PayCodeResourceIntTest {

    private static final String DEFAULT_PAY_CODE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PAY_CODE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PayCodeRepository payCodeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayCodeMockMvc;

    private PayCode payCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PayCodeResource payCodeResource = new PayCodeResource(payCodeRepository);
        this.restPayCodeMockMvc = MockMvcBuilders.standaloneSetup(payCodeResource)
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
    public static PayCode createEntity(EntityManager em) {
        PayCode payCode = new PayCode()
            .payCodeDescription(DEFAULT_PAY_CODE_DESCRIPTION);
        return payCode;
    }

    @Before
    public void initTest() {
        payCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayCode() throws Exception {
        int databaseSizeBeforeCreate = payCodeRepository.findAll().size();

        // Create the PayCode
        restPayCodeMockMvc.perform(post("/api/pay-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payCode)))
            .andExpect(status().isCreated());

        // Validate the PayCode in the database
        List<PayCode> payCodeList = payCodeRepository.findAll();
        assertThat(payCodeList).hasSize(databaseSizeBeforeCreate + 1);
        PayCode testPayCode = payCodeList.get(payCodeList.size() - 1);
        assertThat(testPayCode.getPayCodeDescription()).isEqualTo(DEFAULT_PAY_CODE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPayCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payCodeRepository.findAll().size();

        // Create the PayCode with an existing ID
        payCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayCodeMockMvc.perform(post("/api/pay-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payCode)))
            .andExpect(status().isBadRequest());

        // Validate the PayCode in the database
        List<PayCode> payCodeList = payCodeRepository.findAll();
        assertThat(payCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPayCodes() throws Exception {
        // Initialize the database
        payCodeRepository.saveAndFlush(payCode);

        // Get all the payCodeList
        restPayCodeMockMvc.perform(get("/api/pay-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].payCodeDescription").value(hasItem(DEFAULT_PAY_CODE_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getPayCode() throws Exception {
        // Initialize the database
        payCodeRepository.saveAndFlush(payCode);

        // Get the payCode
        restPayCodeMockMvc.perform(get("/api/pay-codes/{id}", payCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payCode.getId().intValue()))
            .andExpect(jsonPath("$.payCodeDescription").value(DEFAULT_PAY_CODE_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayCode() throws Exception {
        // Get the payCode
        restPayCodeMockMvc.perform(get("/api/pay-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayCode() throws Exception {
        // Initialize the database
        payCodeRepository.saveAndFlush(payCode);

        int databaseSizeBeforeUpdate = payCodeRepository.findAll().size();

        // Update the payCode
        PayCode updatedPayCode = payCodeRepository.findById(payCode.getId()).get();
        // Disconnect from session so that the updates on updatedPayCode are not directly saved in db
        em.detach(updatedPayCode);
        updatedPayCode
            .payCodeDescription(UPDATED_PAY_CODE_DESCRIPTION);

        restPayCodeMockMvc.perform(put("/api/pay-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayCode)))
            .andExpect(status().isOk());

        // Validate the PayCode in the database
        List<PayCode> payCodeList = payCodeRepository.findAll();
        assertThat(payCodeList).hasSize(databaseSizeBeforeUpdate);
        PayCode testPayCode = payCodeList.get(payCodeList.size() - 1);
        assertThat(testPayCode.getPayCodeDescription()).isEqualTo(UPDATED_PAY_CODE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPayCode() throws Exception {
        int databaseSizeBeforeUpdate = payCodeRepository.findAll().size();

        // Create the PayCode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayCodeMockMvc.perform(put("/api/pay-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payCode)))
            .andExpect(status().isBadRequest());

        // Validate the PayCode in the database
        List<PayCode> payCodeList = payCodeRepository.findAll();
        assertThat(payCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePayCode() throws Exception {
        // Initialize the database
        payCodeRepository.saveAndFlush(payCode);

        int databaseSizeBeforeDelete = payCodeRepository.findAll().size();

        // Get the payCode
        restPayCodeMockMvc.perform(delete("/api/pay-codes/{id}", payCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayCode> payCodeList = payCodeRepository.findAll();
        assertThat(payCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayCode.class);
        PayCode payCode1 = new PayCode();
        payCode1.setId(1L);
        PayCode payCode2 = new PayCode();
        payCode2.setId(payCode1.getId());
        assertThat(payCode1).isEqualTo(payCode2);
        payCode2.setId(2L);
        assertThat(payCode1).isNotEqualTo(payCode2);
        payCode1.setId(null);
        assertThat(payCode1).isNotEqualTo(payCode2);
    }
}
