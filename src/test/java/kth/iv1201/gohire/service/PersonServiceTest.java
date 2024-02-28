package kth.iv1201.gohire.service;

import kth.iv1201.gohire.DTO.ApplicantDTO;
import kth.iv1201.gohire.DTO.CreateApplicantRequestDTO;
import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.entity.ApplicationStatusEntity;
import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.entity.RoleEntity;
import kth.iv1201.gohire.repository.ApplicationStatusRepository;
import kth.iv1201.gohire.repository.PersonRepository;
import kth.iv1201.gohire.repository.RoleRepository;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import kth.iv1201.gohire.service.exception.UserNotFoundException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class PersonServiceTest {
    @Mock
    PersonRepository personRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    ApplicationStatusRepository applicationStatusRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    PersonService personService;
    PersonEntity fakePersonEntity;
    RoleEntity roleEntity;
    ApplicationStatusEntity fakeApplicationStatus;
    PersonEntity fakeApplicantPerson;


    @BeforeEach
    public void setUp() {
        Mockito.reset(personRepository);
        Mockito.reset(roleRepository);
        Mockito.reset(applicationStatusRepository);
        Mockito.reset(passwordEncoder);
        this.personService = new PersonService(personRepository, roleRepository, applicationStatusRepository, passwordEncoder);
        this.fakePersonEntity = new PersonEntity();
        fakePersonEntity.setUsername("aValidUsername");
        fakePersonEntity.setPassword("aValidPassword");
        fakePersonEntity.setId(1);
        roleEntity = new RoleEntity();
        roleEntity.setName("recruiter");
        when(roleRepository.findRoleById(2)).thenReturn(roleEntity);
        fakePersonEntity.setRole(roleEntity);

        this.fakeApplicantPerson = new PersonEntity();
        fakeApplicantPerson.setName("ApplicantFirstName");
        fakeApplicantPerson.setSurname("ApplicantLastName");
        this.fakeApplicationStatus = new ApplicationStatusEntity();
        fakeApplicationStatus.setStatus("unhandled");
        fakeApplicantPerson.setApplicationStatus(fakeApplicationStatus);
    }

    @AfterEach
    public void tearDown() {
        personService = null;
        personRepository.delete(fakePersonEntity);
        fakePersonEntity = null;
        roleEntity = null;
        passwordEncoder = null;

    }

    @Test
    public void testIfUserNotFoundExceptionIsThrownIfGivenNonExistingUsername() {
        when(personRepository.findByUsername("esCod")).thenReturn(null);
        assertThrowsExactly(UserNotFoundException.class, () -> personService.fetchLoggedInPersonByUsername("esCod"),
                "The function did not return an expected exception.");
    }

    @Test
    public void testIfMethodReturnsWorkingLoggedInPersonDTO() {
        when(personRepository.findByUsername(fakePersonEntity.getUsername())).thenReturn(fakePersonEntity);
        try {
            LoggedInPersonDTO loggedInUser = personService.fetchLoggedInPersonByUsername(fakePersonEntity.getUsername());
            assertEquals(fakePersonEntity.getUsername(), loggedInUser.getUsername());
        } catch (UserNotFoundException e) {
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
    
    @Test
    void testIfFetchApplicantsReturnsCorrectNumberOfApplicants() {
        int numberOfPersons = 5;
        List<ApplicantDTO> applicants = mockRepositoryAndCallFetchApplicants(numberOfPersons);
        assertEquals(numberOfPersons, applicants.size(),
                "Incorrect number of applicants was returned.");
    }

    @Test
    void testIfFetchApplicantsReturnsCorrectFirstName() {
        List<ApplicantDTO> applicants = mockRepositoryAndCallFetchApplicants(1);
        assertEquals(applicants.get(0).getFirstName(), fakeApplicantPerson.getName(),
                "Incorrect first name in fetched applicant.");
    }

    @Test
    void testIfFetchApplicantsReturnsCorrectLastName() {
        List<ApplicantDTO> applicants = mockRepositoryAndCallFetchApplicants(1);
        assertEquals(applicants.get(0).getLastName(), fakeApplicantPerson.getSurname(),
                "Incorrect surname in fetched applicant.");
    }

    @Test
    void testIfFetchApplicantsReturnsCorrectApplicationStatus() {
        List<ApplicantDTO> applicants = mockRepositoryAndCallFetchApplicants(1);
        assertEquals(applicants.get(0).getStatus(), fakeApplicationStatus.getStatus(),
                "Incorrect status in fetched applicant.");
    }

    private List<ApplicantDTO> mockRepositoryAndCallFetchApplicants(int numberOfPersons) {
        List<PersonEntity> inputPersons = new LinkedList<>();
        for (int i = 0; i < numberOfPersons; i++) {
            inputPersons.add(fakeApplicantPerson);
        }
        when(personRepository.findPersonEntitiesByRoleIs(any())).thenReturn(inputPersons);
        return personService.fetchApplicants();
    }

}
