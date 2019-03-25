package com.lagab.cmanager.web.rest;

import com.lagab.cmanager.CmApp;

import com.lagab.cmanager.domain.User;
import com.lagab.cmanager.repository.UserRepository;
import com.lagab.cmanager.service.UserService;
import com.lagab.cmanager.service.dto.UserDTO;
import com.lagab.cmanager.service.mapper.UserMapper;
import com.lagab.cmanager.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static com.lagab.cmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmApp.class)
public class UserResourceIntTest {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_LANG_KEY = "AAAAAA";
    private static final String UPDATED_LANG_KEY = "BBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_RESET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_RESET_KEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESET_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESET_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserRepository userRepository;

    @Mock
    private UserRepository userRepositoryMock;

    @Autowired
    private UserMapper userMapper;

    @Mock
    private UserService userServiceMock;

    @Autowired
    private UserService userService;

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

    private MockMvc restUserMockMvc;

    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserResource userResource = new UserResource(userService);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
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
    public static User createEntity(EntityManager em) {
        User user = new User()
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .activated(DEFAULT_ACTIVATED)
            .langKey(DEFAULT_LANG_KEY)
            .imageUrl(DEFAULT_IMAGE_URL)
            .activationKey(DEFAULT_ACTIVATION_KEY)
            .resetKey(DEFAULT_RESET_KEY)
            .resetDate(DEFAULT_RESET_DATE);
        return user;
    }

    @Before
    public void initTest() {
        user = createEntity(em);
    }

    @Test
    @Transactional
    public void createUser() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        // Create the User
        UserDTO userDTO = userMapper.toDto(user);
        restUserMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isCreated());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUser.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testUser.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
        assertThat(testUser.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testUser.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testUser.getResetKey()).isEqualTo(DEFAULT_RESET_KEY);
        assertThat(testUser.getResetDate()).isEqualTo(DEFAULT_RESET_DATE);
    }

    @Test
    @Transactional
    public void createUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        // Create the User with an existing ID
        user.setId(1L);
        UserDTO userDTO = userMapper.toDto(user);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRepository.findAll().size();
        // set the field null
        user.setLogin(null);

        // Create the User, which fails.
        UserDTO userDTO = userMapper.toDto(user);

        restUserMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isBadRequest());

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRepository.findAll().size();
        // set the field null
        user.setPassword(null);

        // Create the User, which fails.
        UserDTO userDTO = userMapper.toDto(user);

        restUserMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isBadRequest());

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRepository.findAll().size();
        // set the field null
        user.setEmail(null);

        // Create the User, which fails.
        UserDTO userDTO = userMapper.toDto(user);

        restUserMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isBadRequest());

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRepository.findAll().size();
        // set the field null
        user.setActivated(null);

        // Create the User, which fails.
        UserDTO userDTO = userMapper.toDto(user);

        restUserMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isBadRequest());

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get all the userList
        restUserMockMvc.perform(get("/api/users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY.toString())))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY.toString())))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(DEFAULT_RESET_DATE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUsersWithEagerRelationshipsIsEnabled() throws Exception {
        UserResource userResource = new UserResource(userServiceMock);
        when(userServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserMockMvc.perform(get("/api/users?eagerload=true"))
        .andExpect(status().isOk());

        verify(userServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        UserResource userResource = new UserResource(userServiceMock);
            when(userServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserMockMvc.perform(get("/api/users?eagerload=true"))
        .andExpect(status().isOk());

            verify(userServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get the user
        restUserMockMvc.perform(get("/api/users/{id}", user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(user.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.langKey").value(DEFAULT_LANG_KEY.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.activationKey").value(DEFAULT_ACTIVATION_KEY.toString()))
            .andExpect(jsonPath("$.resetKey").value(DEFAULT_RESET_KEY.toString()))
            .andExpect(jsonPath("$.resetDate").value(DEFAULT_RESET_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUser() throws Exception {
        // Get the user
        restUserMockMvc.perform(get("/api/users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();
        // Disconnect from session so that the updates on updatedUser are not directly saved in db
        em.detach(updatedUser);
        updatedUser
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .imageUrl(UPDATED_IMAGE_URL)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE);
        UserDTO userDTO = userMapper.toDto(updatedUser);

        restUserMockMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isOk());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUser.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testUser.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
        assertThat(testUser.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testUser.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testUser.getResetKey()).isEqualTo(UPDATED_RESET_KEY);
        assertThat(testUser.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUser() throws Exception {
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Create the User
        UserDTO userDTO = userMapper.toDto(user);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserMockMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        int databaseSizeBeforeDelete = userRepository.findAll().size();

        // Delete the user
        restUserMockMvc.perform(delete("/api/users/{id}", user.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User.class);
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(user1.getId());
        assertThat(user1).isEqualTo(user2);
        user2.setId(2L);
        assertThat(user1).isNotEqualTo(user2);
        user1.setId(null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDTO.class);
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setId(1L);
        UserDTO userDTO2 = new UserDTO();
        assertThat(userDTO1).isNotEqualTo(userDTO2);
        userDTO2.setId(userDTO1.getId());
        assertThat(userDTO1).isEqualTo(userDTO2);
        userDTO2.setId(2L);
        assertThat(userDTO1).isNotEqualTo(userDTO2);
        userDTO1.setId(null);
        assertThat(userDTO1).isNotEqualTo(userDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userMapper.fromId(null)).isNull();
    }
}
