package com.cayuse.timesheets.web.rest;

import com.cayuse.timesheets.TimesheetsApp;

import com.cayuse.timesheets.domain.SubCostCategory;
import com.cayuse.timesheets.repository.SubCostCategoryRepository;
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
 * Test class for the SubCostCategoryResource REST controller.
 *
 * @see SubCostCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimesheetsApp.class)
public class SubCostCategoryResourceIntTest {

    private static final String DEFAULT_SUB_COST_CATEGORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SUB_COST_CATEGORY_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SubCostCategoryRepository subCostCategoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubCostCategoryMockMvc;

    private SubCostCategory subCostCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubCostCategoryResource subCostCategoryResource = new SubCostCategoryResource(subCostCategoryRepository);
        this.restSubCostCategoryMockMvc = MockMvcBuilders.standaloneSetup(subCostCategoryResource)
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
    public static SubCostCategory createEntity(EntityManager em) {
        SubCostCategory subCostCategory = new SubCostCategory()
            .subCostCategoryDescription(DEFAULT_SUB_COST_CATEGORY_DESCRIPTION);
        return subCostCategory;
    }

    @Before
    public void initTest() {
        subCostCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubCostCategory() throws Exception {
        int databaseSizeBeforeCreate = subCostCategoryRepository.findAll().size();

        // Create the SubCostCategory
        restSubCostCategoryMockMvc.perform(post("/api/sub-cost-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCostCategory)))
            .andExpect(status().isCreated());

        // Validate the SubCostCategory in the database
        List<SubCostCategory> subCostCategoryList = subCostCategoryRepository.findAll();
        assertThat(subCostCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SubCostCategory testSubCostCategory = subCostCategoryList.get(subCostCategoryList.size() - 1);
        assertThat(testSubCostCategory.getSubCostCategoryDescription()).isEqualTo(DEFAULT_SUB_COST_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSubCostCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subCostCategoryRepository.findAll().size();

        // Create the SubCostCategory with an existing ID
        subCostCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubCostCategoryMockMvc.perform(post("/api/sub-cost-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCostCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SubCostCategory in the database
        List<SubCostCategory> subCostCategoryList = subCostCategoryRepository.findAll();
        assertThat(subCostCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSubCostCategories() throws Exception {
        // Initialize the database
        subCostCategoryRepository.saveAndFlush(subCostCategory);

        // Get all the subCostCategoryList
        restSubCostCategoryMockMvc.perform(get("/api/sub-cost-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subCostCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].subCostCategoryDescription").value(hasItem(DEFAULT_SUB_COST_CATEGORY_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSubCostCategory() throws Exception {
        // Initialize the database
        subCostCategoryRepository.saveAndFlush(subCostCategory);

        // Get the subCostCategory
        restSubCostCategoryMockMvc.perform(get("/api/sub-cost-categories/{id}", subCostCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subCostCategory.getId().intValue()))
            .andExpect(jsonPath("$.subCostCategoryDescription").value(DEFAULT_SUB_COST_CATEGORY_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubCostCategory() throws Exception {
        // Get the subCostCategory
        restSubCostCategoryMockMvc.perform(get("/api/sub-cost-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubCostCategory() throws Exception {
        // Initialize the database
        subCostCategoryRepository.saveAndFlush(subCostCategory);

        int databaseSizeBeforeUpdate = subCostCategoryRepository.findAll().size();

        // Update the subCostCategory
        SubCostCategory updatedSubCostCategory = subCostCategoryRepository.findById(subCostCategory.getId()).get();
        // Disconnect from session so that the updates on updatedSubCostCategory are not directly saved in db
        em.detach(updatedSubCostCategory);
        updatedSubCostCategory
            .subCostCategoryDescription(UPDATED_SUB_COST_CATEGORY_DESCRIPTION);

        restSubCostCategoryMockMvc.perform(put("/api/sub-cost-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubCostCategory)))
            .andExpect(status().isOk());

        // Validate the SubCostCategory in the database
        List<SubCostCategory> subCostCategoryList = subCostCategoryRepository.findAll();
        assertThat(subCostCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCostCategory testSubCostCategory = subCostCategoryList.get(subCostCategoryList.size() - 1);
        assertThat(testSubCostCategory.getSubCostCategoryDescription()).isEqualTo(UPDATED_SUB_COST_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSubCostCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCostCategoryRepository.findAll().size();

        // Create the SubCostCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubCostCategoryMockMvc.perform(put("/api/sub-cost-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCostCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SubCostCategory in the database
        List<SubCostCategory> subCostCategoryList = subCostCategoryRepository.findAll();
        assertThat(subCostCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubCostCategory() throws Exception {
        // Initialize the database
        subCostCategoryRepository.saveAndFlush(subCostCategory);

        int databaseSizeBeforeDelete = subCostCategoryRepository.findAll().size();

        // Get the subCostCategory
        restSubCostCategoryMockMvc.perform(delete("/api/sub-cost-categories/{id}", subCostCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubCostCategory> subCostCategoryList = subCostCategoryRepository.findAll();
        assertThat(subCostCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubCostCategory.class);
        SubCostCategory subCostCategory1 = new SubCostCategory();
        subCostCategory1.setId(1L);
        SubCostCategory subCostCategory2 = new SubCostCategory();
        subCostCategory2.setId(subCostCategory1.getId());
        assertThat(subCostCategory1).isEqualTo(subCostCategory2);
        subCostCategory2.setId(2L);
        assertThat(subCostCategory1).isNotEqualTo(subCostCategory2);
        subCostCategory1.setId(null);
        assertThat(subCostCategory1).isNotEqualTo(subCostCategory2);
    }
}
