package kth.iv1201.gohire.service;

import kth.iv1201.gohire.DTO.ApplicantDTO;
import kth.iv1201.gohire.DTO.ChangeApplicationStatusRequestDTO;
import kth.iv1201.gohire.DTO.CreateApplicantRequestDTO;
import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.entity.ApplicationStatusEntity;
import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.entity.RoleEntity;
import kth.iv1201.gohire.repository.ApplicationStatusRepository;
import kth.iv1201.gohire.repository.PersonRepository;
import kth.iv1201.gohire.repository.RoleRepository;
import kth.iv1201.gohire.service.exception.ApplicationHandledException;
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
    PersonEntity fakeRecruiterEntity;
    RoleEntity roleEntity;
    ApplicationStatusEntity fakeUnhandledApplicationStatus;
    ApplicationStatusEntity fakeAcceptedApplicationStatus;

    ApplicationStatusEntity fakeRejectedApplicationStatus;
    PersonEntity fakeUnhandledApplicantPerson;
    PersonEntity fakeHandledApplicantPerson;




    @BeforeEach
    public void setUp() {
        Mockito.reset(personRepository);
        Mockito.reset(roleRepository);
        Mockito.reset(applicationStatusRepository);
        this.personService = new PersonService(personRepository, roleRepository, applicationStatusRepository,passwordEncoder);
        this.fakeRecruiterEntity = new PersonEntity();
        fakeRecruiterEntity.setUsername("aValidUsername");
        fakeRecruiterEntity.setPassword("aValidPassword");
        fakeRecruiterEntity.setId(1);
        roleEntity = new RoleEntity();
        roleEntity.setName("recruiter");
        when(roleRepository.findRoleById(2)).thenReturn(roleEntity);
        fakeRecruiterEntity.setRole(roleEntity);

        this.fakeAcceptedApplicationStatus = new ApplicationStatusEntity();
        fakeAcceptedApplicationStatus.setStatus("handled");
        fakeAcceptedApplicationStatus.setId(1);

        this.fakeRejectedApplicationStatus = new ApplicationStatusEntity();
        fakeRejectedApplicationStatus.setStatus("handled");
        fakeRejectedApplicationStatus.setId(2);

        this.fakeUnhandledApplicationStatus = new ApplicationStatusEntity();
        fakeUnhandledApplicationStatus.setStatus("unhandled");
        fakeUnhandledApplicationStatus.setId(3);

        this.fakeHandledApplicantPerson = new PersonEntity();
        fakeHandledApplicantPerson.setName("ApplicantFirstName");
        fakeHandledApplicantPerson.setSurname("ApplicantLastName");
        fakeHandledApplicantPerson.setId(2);
        fakeHandledApplicantPerson.setApplicationStatus(fakeAcceptedApplicationStatus);

        this.fakeUnhandledApplicantPerson = new PersonEntity();
        fakeUnhandledApplicantPerson.setName("ApplicantFirstName");
        fakeUnhandledApplicantPerson.setSurname("ApplicantLastName");
        fakeUnhandledApplicantPerson.setId(3);
        fakeUnhandledApplicantPerson.setApplicationStatus(fakeUnhandledApplicationStatus);
    }

    @AfterEach
    public void tearDown() {
        personService = null;
        personRepository.delete(fakeRecruiterEntity);
        fakeRecruiterEntity = null;
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
        when(personRepository.findByUsername(fakeRecruiterEntity.getUsername())).thenReturn(fakeRecruiterEntity);
        try {
            LoggedInPersonDTO loggedInUser = personService.fetchLoggedInPersonByUsername(fakeRecruiterEntity.getUsername());
            assertEquals(fakeRecruiterEntity.getUsername(), loggedInUser.getUsername());
        } catch (UserNotFoundException e) {
            fail("The function failed to fetch the correct data\n");
            e.printStackTrace();
        }
    }

    @Test
    public void testIfCreateUserReturnsALoggedInPersonDTO() {
        CreateApplicantRequestDTO request = new CreateApplicantRequestDTO(null,null,null,null, fakeRecruiterEntity.getUsername(),null);
        try {
            when(personRepository.save(any(PersonEntity.class))).thenReturn(fakeRecruiterEntity);
            LoggedInPersonDTO user = personService.createApplicantAccount(request);
            assertEquals(LoggedInPersonDTO.class, user.getClass(), "Did not return a LoggedInPersonDTO");
        } catch (UserCreationFailedException e) {
            fail("The user creation failed even though valid arguments were sent");
        }
    }

    @Test
    public void testIfCreateUserThrowsUserCreateFailedException() {
        CreateApplicantRequestDTO request = new CreateApplicantRequestDTO(null,null,null,null, fakeRecruiterEntity.getUsername(),null);
        when(personRepository.existsByUsername(fakeRecruiterEntity.getUsername())).thenReturn(true);
        try {
            personService.createApplicantAccount(request);
            fail("Method returned correctly when it should have thrown an exception");
        } catch (Exception e) {
            assertEquals(UserCreationFailedException.class, e.getClass(), "Method doesn't throw right kind of exception, throws: " + e.getClass());
        }
    }

    @Test void testIfCreateUserConstructsReturnDTOWithUsernameProperly() {
        CreateApplicantRequestDTO request = new CreateApplicantRequestDTO(null,null,null,null, fakeRecruiterEntity.getUsername(),null);
        try {
            when(personRepository.save(any(PersonEntity.class))).thenReturn(fakeRecruiterEntity);
            LoggedInPersonDTO user = personService.createApplicantAccount(request);
            assertEquals(fakeRecruiterEntity.getUsername(), user.getUsername(), "DTO does not contain correct information");
        } catch (UserCreationFailedException e) {
            fail("The user creation failed even though valid arguments were sent");
        }
    }

    @Test void testIfCreateUserConstructsReturnDTOWithRoleProperly() {
        CreateApplicantRequestDTO request = new CreateApplicantRequestDTO(null,null,null,null, fakeRecruiterEntity.getUsername(),null);
        try {
            when(personRepository.save(any(PersonEntity.class))).thenReturn(fakeRecruiterEntity);
            LoggedInPersonDTO user = personService.createApplicantAccount(request);
            assertEquals(fakeRecruiterEntity.getRole().getName(), user.getRole(), "DTO does not contain correct information");
        } catch (UserCreationFailedException e) {
            fail("The user creation failed even though valid arguments were sent");
        }
    }

    @Test void testIfCreateUserConstructsReturnDTOWithIdProperly() {
        CreateApplicantRequestDTO request = new CreateApplicantRequestDTO(null,null,null,null, fakeRecruiterEntity.getUsername(),null);
        try {
            when(personRepository.save(any(PersonEntity.class))).thenReturn(fakeRecruiterEntity);
            LoggedInPersonDTO user = personService.createApplicantAccount(request);
            assertEquals(fakeRecruiterEntity.getId(), user.getId(), "DTO does not contain correct information");
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
        assertEquals(applicants.get(0).getFirstName(), fakeUnhandledApplicantPerson.getName(),
                "Incorrect first name in fetched applicant.");
    }

    @Test
    void testIfFetchApplicantsReturnsCorrectLastName() {
        List<ApplicantDTO> applicants = mockRepositoryAndCallFetchApplicants(1);
        assertEquals(applicants.get(0).getLastName(), fakeUnhandledApplicantPerson.getSurname(),
                "Incorrect surname in fetched applicant.");
    }

    @Test
    void testIfFetchApplicantsReturnsCorrectApplicationStatus() {
        List<ApplicantDTO> applicants = mockRepositoryAndCallFetchApplicants(1);
        assertEquals(applicants.get(0).getStatus(), fakeUnhandledApplicationStatus.getStatus(),
                "Incorrect status in fetched applicant.");
    }

    @Test
    void testIfApplicationHandledExceptionIsThrownIfGivenHandledUser() {
        ChangeApplicationStatusRequestDTO allReadyHandledUserRequest = new ChangeApplicationStatusRequestDTO(fakeHandledApplicantPerson.getId(),
                "status", null, null);
        when(personRepository.findPersonById(fakeHandledApplicantPerson.getId())).thenReturn(fakeHandledApplicantPerson);
        assertThrowsExactly(ApplicationHandledException.class, () -> personService.changeApplicantStatus(allReadyHandledUserRequest),
                    "The function did not return an expected exception.");
    }
    @Test
    void testIfChangeApplicantStatusReturnDTOWithIdProperly(){
        ChangeApplicationStatusRequestDTO unhandledUserRequest = new ChangeApplicationStatusRequestDTO(fakeUnhandledApplicantPerson.getId(),
                "status", null, null);
        when(personRepository.findPersonById(fakeUnhandledApplicantPerson.getId())).thenReturn(fakeUnhandledApplicantPerson);
        when(personRepository.save(fakeUnhandledApplicantPerson)).thenReturn(fakeUnhandledApplicantPerson);
        try {
            ApplicantDTO returnedApplicantDTO = personService.changeApplicantStatus(unhandledUserRequest);
            assertEquals(fakeUnhandledApplicantPerson.getId(), returnedApplicantDTO.getId(),"DTO does not contain correct information");
        } catch (ApplicationHandledException e) {
            fail("The change applicant status failed even though valid arguments were sent");
        }
    }
    @Test
    void testIfChangeApplicantStatusReturnDTOWithNameProperly(){
        ChangeApplicationStatusRequestDTO unhandledUserRequest = new ChangeApplicationStatusRequestDTO(fakeUnhandledApplicantPerson.getId(),
                "status", "userName", null);
        when(personRepository.findPersonById(fakeUnhandledApplicantPerson.getId())).thenReturn(fakeUnhandledApplicantPerson);
        //when(applicationStatusRepository.findById(1)).thenReturn(fakeAcceptedApplicationStatus);
        when(personRepository.save(fakeUnhandledApplicantPerson)).thenReturn(fakeUnhandledApplicantPerson);
        try {
            ApplicantDTO returnedApplicantDTO = personService.changeApplicantStatus(unhandledUserRequest);
            assertEquals(fakeUnhandledApplicantPerson.getName(), returnedApplicantDTO.getFirstName(),"DTO does not contain correct information");
        } catch (ApplicationHandledException e) {
            fail("The change applicant status failed even though valid arguments were sent");
        }
    }
    @Test
    void testIfChangeApplicantStatusReturnDTOWithSurnameProperly(){
        ChangeApplicationStatusRequestDTO unhandledUserRequest = new ChangeApplicationStatusRequestDTO(fakeUnhandledApplicantPerson.getId(),
                "status", "userName", null);
        when(personRepository.findPersonById(fakeUnhandledApplicantPerson.getId())).thenReturn(fakeUnhandledApplicantPerson);
        when(personRepository.save(fakeUnhandledApplicantPerson)).thenReturn(fakeUnhandledApplicantPerson);
        try {
            ApplicantDTO returnedApplicantDTO = personService.changeApplicantStatus(unhandledUserRequest);
            assertEquals(fakeUnhandledApplicantPerson.getSurname(), returnedApplicantDTO.getLastName(),"DTO does not contain correct information");
        } catch (ApplicationHandledException e) {
            fail("The change applicant status failed even though valid arguments were sent");
        }
    }
    @Test
    void testIfChangeApplicantStatusReturnDTOWithAcceptedStatusProperly(){
        ChangeApplicationStatusRequestDTO unhandledUserRequest = new ChangeApplicationStatusRequestDTO(fakeUnhandledApplicantPerson.getId(),
                "accepted", null, null);
        when(personRepository.findPersonById(fakeUnhandledApplicantPerson.getId())).thenReturn(fakeUnhandledApplicantPerson);
        when(personRepository.save(fakeUnhandledApplicantPerson)).thenReturn(fakeUnhandledApplicantPerson);
        try {
            ApplicantDTO returnedApplicantDTO = personService.changeApplicantStatus(unhandledUserRequest);
            assertEquals(unhandledUserRequest.getNewStatus(), returnedApplicantDTO.getStatus(),"DTO does not contain correct information");
        } catch (ApplicationHandledException e) {
            fail("The change applicant status failed even though valid arguments were sent");
        }
    }
    @Test
    void testIfChangeApplicantStatusReturnDTOWithRejectedStatusProperly(){
        ChangeApplicationStatusRequestDTO unhandledUserRequest = new ChangeApplicationStatusRequestDTO(fakeUnhandledApplicantPerson.getId(),
                "rejected", null, null);
        when(personRepository.findPersonById(fakeUnhandledApplicantPerson.getId())).thenReturn(fakeUnhandledApplicantPerson);
        when(personRepository.save(fakeUnhandledApplicantPerson)).thenReturn(fakeUnhandledApplicantPerson);
        try {
            ApplicantDTO returnedApplicantDTO = personService.changeApplicantStatus(unhandledUserRequest);
            assertEquals(unhandledUserRequest.getNewStatus(), returnedApplicantDTO.getStatus(),"DTO does not contain correct information");
        } catch (ApplicationHandledException e) {
            fail("The change applicant status failed even though valid arguments were sent");
        }
    }
    private List<ApplicantDTO> mockRepositoryAndCallFetchApplicants(int numberOfPersons) {
        List<PersonEntity> inputPersons = new LinkedList<>();
        for (int i = 0; i < numberOfPersons; i++) {
            inputPersons.add(fakeUnhandledApplicantPerson);
        }
        when(personRepository.findPersonEntitiesByRoleIs(any())).thenReturn(inputPersons);
        return personService.fetchApplicants();
    }

}
