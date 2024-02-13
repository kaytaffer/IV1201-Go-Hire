package kth.iv1201.gohire.service;

import kth.iv1201.gohire.DTO.CreateApplicantRequestDTO;
import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.DTO.LoginRequestDTO;
import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.entity.RoleEntity;
import kth.iv1201.gohire.repository.PersonRepository;
import kth.iv1201.gohire.repository.RoleRepository;
import kth.iv1201.gohire.service.exception.LoginFailedException;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonServiceTest {
    @Mock
    PersonRepository personRepository;
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    PersonService personService;
    PersonEntity fakePersonEntity;
    RoleEntity roleEntity;

    @BeforeEach
    public void setUp() {
        this.personService = new PersonService(personRepository, roleRepository);
        this.fakePersonEntity = new PersonEntity();
        fakePersonEntity.setUsername("aValidUsername");
        fakePersonEntity.setPassword("aValidPassword");
        fakePersonEntity.setId(1);
        roleEntity = new RoleEntity();
        roleEntity.setName("recruiter");
        when(roleRepository.findRoleById(2)).thenReturn(roleEntity);
        fakePersonEntity.setRole(roleEntity);
    }

    @AfterEach
    public void tearDown() {
        personService = null;
        personRepository.delete(fakePersonEntity);
        fakePersonEntity = null;
        roleEntity = null;
        Mockito.reset(personRepository);
        Mockito.reset(roleRepository);
    }

    @Test
    public void testIfLoginFailedExceptionIsThrownIfGivenWrongUsernameAndPassword() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("esCod", "whatever");
        when(personRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(null);
        try {
            personService.login(loginRequest);
            fail("The function did not return an expected exception.");
        } catch (LoginFailedException exception) {
            assertEquals(exception.getClass(), LoginFailedException.class);
        }
    }

    @Test
    public void testIfMethodReturnsWorkingLoggedInPersonDTO() {
        LoginRequestDTO loginRequest = new LoginRequestDTO(fakePersonEntity.getUsername(), fakePersonEntity.getPassword());
        when(personRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(fakePersonEntity);
        try {
            LoggedInPersonDTO loggedInUser = personService.login(loginRequest);
            assertEquals(fakePersonEntity.getUsername(), loggedInUser.getUsername());
        } catch (LoginFailedException e) {
            fail("The function failed to fetch the correct data\n");
            e.printStackTrace();
        }
    }

    @Test
    public void testIfCreateUserReturnsALoggedInPersonDTO() {
        CreateApplicantRequestDTO request = new CreateApplicantRequestDTO(null,null,null,null, fakePersonEntity.getUsername(),null);
        try {
            when(personRepository.save(any(PersonEntity.class))).thenReturn(fakePersonEntity);
            LoggedInPersonDTO user = personService.createApplicantAccount(request);
            assertEquals(LoggedInPersonDTO.class, user.getClass(), "Did not return a LoggedInPersonDTO");
        } catch (UserCreationFailedException e) {
            fail("The user creation failed even though valid arguments were sent");
        }
    }

    @Test
    public void testIfCreateUserThrowsUserCreateFailedException() {
        CreateApplicantRequestDTO request = new CreateApplicantRequestDTO(null,null,null,null, fakePersonEntity.getUsername(),null);
        when(personRepository.existsByUsername(fakePersonEntity.getUsername())).thenReturn(true);
        try {
            personService.createApplicantAccount(request);
            fail("Method returned correctly when it should have thrown an exception");
        } catch (Exception e) {
            assertEquals(UserCreationFailedException.class, e.getClass(), "Method doesn't throw right kind of exception, throws: " + e.getClass());
        }
    }

    @Test void testIfCreateUserConstructsReturnDTOWithUsernameProperly() {
        CreateApplicantRequestDTO request = new CreateApplicantRequestDTO(null,null,null,null, fakePersonEntity.getUsername(),null);
        try {
            when(personRepository.save(any(PersonEntity.class))).thenReturn(fakePersonEntity);
            LoggedInPersonDTO user = personService.createApplicantAccount(request);
            assertEquals(fakePersonEntity.getUsername(), user.getUsername(), "DTO does not contain correct information");
        } catch (UserCreationFailedException e) {
            fail("The user creation failed even though valid arguments were sent");
        }
    }

    @Test void testIfCreateUserConstructsReturnDTOWithRoleProperly() {
        CreateApplicantRequestDTO request = new CreateApplicantRequestDTO(null,null,null,null, fakePersonEntity.getUsername(),null);
        try {
            when(personRepository.save(any(PersonEntity.class))).thenReturn(fakePersonEntity);
            LoggedInPersonDTO user = personService.createApplicantAccount(request);
            assertEquals(fakePersonEntity.getRole().getName(), user.getRole(), "DTO does not contain correct information");
        } catch (UserCreationFailedException e) {
            fail("The user creation failed even though valid arguments were sent");
        }
    }

    @Test void testIfCreateUserConstructsReturnDTOWithIdProperly() {
        CreateApplicantRequestDTO request = new CreateApplicantRequestDTO(null,null,null,null, fakePersonEntity.getUsername(),null);
        try {
            when(personRepository.save(any(PersonEntity.class))).thenReturn(fakePersonEntity);
            LoggedInPersonDTO user = personService.createApplicantAccount(request);
            assertEquals(fakePersonEntity.getId(), user.getId(), "DTO does not contain correct information");
        } catch (UserCreationFailedException e) {
            fail("The user creation failed even though valid arguments were sent");
        }
    }

}
