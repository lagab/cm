package com.lagab.cmanager.web.rest;

import com.lagab.cmanager.CmApp;

import com.lagab.cmanager.domain.Attachment;
import com.lagab.cmanager.repository.AttachmentRepository;
import com.lagab.cmanager.service.AttachmentService;
import com.lagab.cmanager.service.dto.AttachmentDTO;
import com.lagab.cmanager.service.mapper.AttachmentMapper;
import com.lagab.cmanager.web.rest.errors.ExceptionTranslator;
import com.lagab.cmanager.service.dto.AttachmentCriteria;
import com.lagab.cmanager.service.AttachmentQueryService;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.lagab.cmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lagab.cmanager.domain.enumeration.EntityType;
/**
 * Test class for the AttachmentResource REST controller.
 *
 * @see AttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmApp.class)
public class AttachmentResourceIntTest {

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;

    private static final EntityType DEFAULT_ENTITY_TYPE = EntityType.CONTRACT;
    private static final EntityType UPDATED_ENTITY_TYPE = EntityType.CONTRACT;

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISK_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_DISK_FILENAME = "BBBBBBBBBB";

    private static final Long DEFAULT_FILE_SIZE = 1L;
    private static final Long UPDATED_FILE_SIZE = 2L;

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DOWNLOADS = 1;
    private static final Integer UPDATED_DOWNLOADS = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentQueryService attachmentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAttachmentMockMvc;

    private Attachment attachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttachmentResource attachmentResource = new AttachmentResource(attachmentService, attachmentQueryService);
        this.restAttachmentMockMvc = MockMvcBuilders.standaloneSetup(attachmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachment createEntity(EntityManager em) {
        Attachment attachment = new Attachment()
            .entityId(DEFAULT_ENTITY_ID)
            .entityType(DEFAULT_ENTITY_TYPE)
            .filename(DEFAULT_FILENAME)
            .diskFilename(DEFAULT_DISK_FILENAME)
            .fileSize(DEFAULT_FILE_SIZE)
            .contentType(DEFAULT_CONTENT_TYPE)
            .downloads(DEFAULT_DOWNLOADS)
            .description(DEFAULT_DESCRIPTION);
        return attachment;
    }

    @Before
    public void initTest() {
        attachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttachment() throws Exception {
        int databaseSizeBeforeCreate = attachmentRepository.findAll().size();

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);
        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeCreate + 1);
        Attachment testAttachment = attachmentList.get(attachmentList.size() - 1);
        assertThat(testAttachment.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testAttachment.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testAttachment.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testAttachment.getDiskFilename()).isEqualTo(DEFAULT_DISK_FILENAME);
        assertThat(testAttachment.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testAttachment.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testAttachment.getDownloads()).isEqualTo(DEFAULT_DOWNLOADS);
        assertThat(testAttachment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attachmentRepository.findAll().size();

        // Create the Attachment with an existing ID
        attachment.setId(1L);
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentRepository.findAll().size();
        // set the field null
        attachment.setEntityId(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEntityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentRepository.findAll().size();
        // set the field null
        attachment.setEntityType(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentRepository.findAll().size();
        // set the field null
        attachment.setFilename(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiskFilenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentRepository.findAll().size();
        // set the field null
        attachment.setDiskFilename(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentRepository.findAll().size();
        // set the field null
        attachment.setFileSize(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentRepository.findAll().size();
        // set the field null
        attachment.setContentType(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDownloadsIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentRepository.findAll().size();
        // set the field null
        attachment.setDownloads(null);

        // Create the Attachment, which fails.
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttachments() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList
        restAttachmentMockMvc.perform(get("/api/attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME.toString())))
            .andExpect(jsonPath("$.[*].diskFilename").value(hasItem(DEFAULT_DISK_FILENAME.toString())))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].downloads").value(hasItem(DEFAULT_DOWNLOADS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get the attachment
        restAttachmentMockMvc.perform(get("/api/attachments/{id}", attachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attachment.getId().intValue()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE.toString()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME.toString()))
            .andExpect(jsonPath("$.diskFilename").value(DEFAULT_DISK_FILENAME.toString()))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.downloads").value(DEFAULT_DOWNLOADS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllAttachmentsByEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where entityId equals to DEFAULT_ENTITY_ID
        defaultAttachmentShouldBeFound("entityId.equals=" + DEFAULT_ENTITY_ID);

        // Get all the attachmentList where entityId equals to UPDATED_ENTITY_ID
        defaultAttachmentShouldNotBeFound("entityId.equals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where entityId in DEFAULT_ENTITY_ID or UPDATED_ENTITY_ID
        defaultAttachmentShouldBeFound("entityId.in=" + DEFAULT_ENTITY_ID + "," + UPDATED_ENTITY_ID);

        // Get all the attachmentList where entityId equals to UPDATED_ENTITY_ID
        defaultAttachmentShouldNotBeFound("entityId.in=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where entityId is not null
        defaultAttachmentShouldBeFound("entityId.specified=true");

        // Get all the attachmentList where entityId is null
        defaultAttachmentShouldNotBeFound("entityId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttachmentsByEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where entityId greater than or equals to DEFAULT_ENTITY_ID
        defaultAttachmentShouldBeFound("entityId.greaterOrEqualThan=" + DEFAULT_ENTITY_ID);

        // Get all the attachmentList where entityId greater than or equals to UPDATED_ENTITY_ID
        defaultAttachmentShouldNotBeFound("entityId.greaterOrEqualThan=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where entityId less than or equals to DEFAULT_ENTITY_ID
        defaultAttachmentShouldNotBeFound("entityId.lessThan=" + DEFAULT_ENTITY_ID);

        // Get all the attachmentList where entityId less than or equals to UPDATED_ENTITY_ID
        defaultAttachmentShouldBeFound("entityId.lessThan=" + UPDATED_ENTITY_ID);
    }


    @Test
    @Transactional
    public void getAllAttachmentsByEntityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where entityType equals to DEFAULT_ENTITY_TYPE
        defaultAttachmentShouldBeFound("entityType.equals=" + DEFAULT_ENTITY_TYPE);

        // Get all the attachmentList where entityType equals to UPDATED_ENTITY_TYPE
        defaultAttachmentShouldNotBeFound("entityType.equals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByEntityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where entityType in DEFAULT_ENTITY_TYPE or UPDATED_ENTITY_TYPE
        defaultAttachmentShouldBeFound("entityType.in=" + DEFAULT_ENTITY_TYPE + "," + UPDATED_ENTITY_TYPE);

        // Get all the attachmentList where entityType equals to UPDATED_ENTITY_TYPE
        defaultAttachmentShouldNotBeFound("entityType.in=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByEntityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where entityType is not null
        defaultAttachmentShouldBeFound("entityType.specified=true");

        // Get all the attachmentList where entityType is null
        defaultAttachmentShouldNotBeFound("entityType.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttachmentsByFilenameIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where filename equals to DEFAULT_FILENAME
        defaultAttachmentShouldBeFound("filename.equals=" + DEFAULT_FILENAME);

        // Get all the attachmentList where filename equals to UPDATED_FILENAME
        defaultAttachmentShouldNotBeFound("filename.equals=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByFilenameIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where filename in DEFAULT_FILENAME or UPDATED_FILENAME
        defaultAttachmentShouldBeFound("filename.in=" + DEFAULT_FILENAME + "," + UPDATED_FILENAME);

        // Get all the attachmentList where filename equals to UPDATED_FILENAME
        defaultAttachmentShouldNotBeFound("filename.in=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByFilenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where filename is not null
        defaultAttachmentShouldBeFound("filename.specified=true");

        // Get all the attachmentList where filename is null
        defaultAttachmentShouldNotBeFound("filename.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttachmentsByDiskFilenameIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where diskFilename equals to DEFAULT_DISK_FILENAME
        defaultAttachmentShouldBeFound("diskFilename.equals=" + DEFAULT_DISK_FILENAME);

        // Get all the attachmentList where diskFilename equals to UPDATED_DISK_FILENAME
        defaultAttachmentShouldNotBeFound("diskFilename.equals=" + UPDATED_DISK_FILENAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByDiskFilenameIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where diskFilename in DEFAULT_DISK_FILENAME or UPDATED_DISK_FILENAME
        defaultAttachmentShouldBeFound("diskFilename.in=" + DEFAULT_DISK_FILENAME + "," + UPDATED_DISK_FILENAME);

        // Get all the attachmentList where diskFilename equals to UPDATED_DISK_FILENAME
        defaultAttachmentShouldNotBeFound("diskFilename.in=" + UPDATED_DISK_FILENAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByDiskFilenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where diskFilename is not null
        defaultAttachmentShouldBeFound("diskFilename.specified=true");

        // Get all the attachmentList where diskFilename is null
        defaultAttachmentShouldNotBeFound("diskFilename.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttachmentsByFileSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize equals to DEFAULT_FILE_SIZE
        defaultAttachmentShouldBeFound("fileSize.equals=" + DEFAULT_FILE_SIZE);

        // Get all the attachmentList where fileSize equals to UPDATED_FILE_SIZE
        defaultAttachmentShouldNotBeFound("fileSize.equals=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByFileSizeIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize in DEFAULT_FILE_SIZE or UPDATED_FILE_SIZE
        defaultAttachmentShouldBeFound("fileSize.in=" + DEFAULT_FILE_SIZE + "," + UPDATED_FILE_SIZE);

        // Get all the attachmentList where fileSize equals to UPDATED_FILE_SIZE
        defaultAttachmentShouldNotBeFound("fileSize.in=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByFileSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize is not null
        defaultAttachmentShouldBeFound("fileSize.specified=true");

        // Get all the attachmentList where fileSize is null
        defaultAttachmentShouldNotBeFound("fileSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttachmentsByFileSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize greater than or equals to DEFAULT_FILE_SIZE
        defaultAttachmentShouldBeFound("fileSize.greaterOrEqualThan=" + DEFAULT_FILE_SIZE);

        // Get all the attachmentList where fileSize greater than or equals to UPDATED_FILE_SIZE
        defaultAttachmentShouldNotBeFound("fileSize.greaterOrEqualThan=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByFileSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where fileSize less than or equals to DEFAULT_FILE_SIZE
        defaultAttachmentShouldNotBeFound("fileSize.lessThan=" + DEFAULT_FILE_SIZE);

        // Get all the attachmentList where fileSize less than or equals to UPDATED_FILE_SIZE
        defaultAttachmentShouldBeFound("fileSize.lessThan=" + UPDATED_FILE_SIZE);
    }


    @Test
    @Transactional
    public void getAllAttachmentsByContentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where contentType equals to DEFAULT_CONTENT_TYPE
        defaultAttachmentShouldBeFound("contentType.equals=" + DEFAULT_CONTENT_TYPE);

        // Get all the attachmentList where contentType equals to UPDATED_CONTENT_TYPE
        defaultAttachmentShouldNotBeFound("contentType.equals=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByContentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where contentType in DEFAULT_CONTENT_TYPE or UPDATED_CONTENT_TYPE
        defaultAttachmentShouldBeFound("contentType.in=" + DEFAULT_CONTENT_TYPE + "," + UPDATED_CONTENT_TYPE);

        // Get all the attachmentList where contentType equals to UPDATED_CONTENT_TYPE
        defaultAttachmentShouldNotBeFound("contentType.in=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByContentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where contentType is not null
        defaultAttachmentShouldBeFound("contentType.specified=true");

        // Get all the attachmentList where contentType is null
        defaultAttachmentShouldNotBeFound("contentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttachmentsByDownloadsIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where downloads equals to DEFAULT_DOWNLOADS
        defaultAttachmentShouldBeFound("downloads.equals=" + DEFAULT_DOWNLOADS);

        // Get all the attachmentList where downloads equals to UPDATED_DOWNLOADS
        defaultAttachmentShouldNotBeFound("downloads.equals=" + UPDATED_DOWNLOADS);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByDownloadsIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where downloads in DEFAULT_DOWNLOADS or UPDATED_DOWNLOADS
        defaultAttachmentShouldBeFound("downloads.in=" + DEFAULT_DOWNLOADS + "," + UPDATED_DOWNLOADS);

        // Get all the attachmentList where downloads equals to UPDATED_DOWNLOADS
        defaultAttachmentShouldNotBeFound("downloads.in=" + UPDATED_DOWNLOADS);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByDownloadsIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where downloads is not null
        defaultAttachmentShouldBeFound("downloads.specified=true");

        // Get all the attachmentList where downloads is null
        defaultAttachmentShouldNotBeFound("downloads.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttachmentsByDownloadsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where downloads greater than or equals to DEFAULT_DOWNLOADS
        defaultAttachmentShouldBeFound("downloads.greaterOrEqualThan=" + DEFAULT_DOWNLOADS);

        // Get all the attachmentList where downloads greater than or equals to UPDATED_DOWNLOADS
        defaultAttachmentShouldNotBeFound("downloads.greaterOrEqualThan=" + UPDATED_DOWNLOADS);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByDownloadsIsLessThanSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where downloads less than or equals to DEFAULT_DOWNLOADS
        defaultAttachmentShouldNotBeFound("downloads.lessThan=" + DEFAULT_DOWNLOADS);

        // Get all the attachmentList where downloads less than or equals to UPDATED_DOWNLOADS
        defaultAttachmentShouldBeFound("downloads.lessThan=" + UPDATED_DOWNLOADS);
    }


    @Test
    @Transactional
    public void getAllAttachmentsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where description equals to DEFAULT_DESCRIPTION
        defaultAttachmentShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the attachmentList where description equals to UPDATED_DESCRIPTION
        defaultAttachmentShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAttachmentShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the attachmentList where description equals to UPDATED_DESCRIPTION
        defaultAttachmentShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where description is not null
        defaultAttachmentShouldBeFound("description.specified=true");

        // Get all the attachmentList where description is null
        defaultAttachmentShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAttachmentShouldBeFound(String filter) throws Exception {
        restAttachmentMockMvc.perform(get("/api/attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)))
            .andExpect(jsonPath("$.[*].diskFilename").value(hasItem(DEFAULT_DISK_FILENAME)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].downloads").value(hasItem(DEFAULT_DOWNLOADS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restAttachmentMockMvc.perform(get("/api/attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAttachmentShouldNotBeFound(String filter) throws Exception {
        restAttachmentMockMvc.perform(get("/api/attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttachmentMockMvc.perform(get("/api/attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAttachment() throws Exception {
        // Get the attachment
        restAttachmentMockMvc.perform(get("/api/attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        int databaseSizeBeforeUpdate = attachmentRepository.findAll().size();

        // Update the attachment
        Attachment updatedAttachment = attachmentRepository.findById(attachment.getId()).get();
        // Disconnect from session so that the updates on updatedAttachment are not directly saved in db
        em.detach(updatedAttachment);
        updatedAttachment
            .entityId(UPDATED_ENTITY_ID)
            .entityType(UPDATED_ENTITY_TYPE)
            .filename(UPDATED_FILENAME)
            .diskFilename(UPDATED_DISK_FILENAME)
            .fileSize(UPDATED_FILE_SIZE)
            .contentType(UPDATED_CONTENT_TYPE)
            .downloads(UPDATED_DOWNLOADS)
            .description(UPDATED_DESCRIPTION);
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(updatedAttachment);

        restAttachmentMockMvc.perform(put("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isOk());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeUpdate);
        Attachment testAttachment = attachmentList.get(attachmentList.size() - 1);
        assertThat(testAttachment.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testAttachment.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testAttachment.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testAttachment.getDiskFilename()).isEqualTo(UPDATED_DISK_FILENAME);
        assertThat(testAttachment.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testAttachment.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testAttachment.getDownloads()).isEqualTo(UPDATED_DOWNLOADS);
        assertThat(testAttachment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAttachment() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRepository.findAll().size();

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentMockMvc.perform(put("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        int databaseSizeBeforeDelete = attachmentRepository.findAll().size();

        // Delete the attachment
        restAttachmentMockMvc.perform(delete("/api/attachments/{id}", attachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attachment.class);
        Attachment attachment1 = new Attachment();
        attachment1.setId(1L);
        Attachment attachment2 = new Attachment();
        attachment2.setId(attachment1.getId());
        assertThat(attachment1).isEqualTo(attachment2);
        attachment2.setId(2L);
        assertThat(attachment1).isNotEqualTo(attachment2);
        attachment1.setId(null);
        assertThat(attachment1).isNotEqualTo(attachment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentDTO.class);
        AttachmentDTO attachmentDTO1 = new AttachmentDTO();
        attachmentDTO1.setId(1L);
        AttachmentDTO attachmentDTO2 = new AttachmentDTO();
        assertThat(attachmentDTO1).isNotEqualTo(attachmentDTO2);
        attachmentDTO2.setId(attachmentDTO1.getId());
        assertThat(attachmentDTO1).isEqualTo(attachmentDTO2);
        attachmentDTO2.setId(2L);
        assertThat(attachmentDTO1).isNotEqualTo(attachmentDTO2);
        attachmentDTO1.setId(null);
        assertThat(attachmentDTO1).isNotEqualTo(attachmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(attachmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(attachmentMapper.fromId(null)).isNull();
    }
}
