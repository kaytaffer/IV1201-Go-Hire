package kth.iv1201.gohire.service;

import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.DTO.LoginRequestDTO;
import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.entity.RoleEntity;
import kth.iv1201.gohire.repository.PersonRepository;
import kth.iv1201.gohire.service.exception.LoginFailedException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonServiceTest {
    @Mock
    PersonRepository personRepository;
    @InjectMocks
    PersonService personService;
    PersonEntity fakePersonEntity;
    RoleEntity roleEntity;

    @BeforeEach
    public void setUp() {
        this.personService = new PersonService(personRepository);
        this.fakePersonEntity = new PersonEntity();
        fakePersonEntity.setName("aValidUsername");
        fakePersonEntity.setPassword("aValidPassword");
        roleEntity = new RoleEntity();
        roleEntity.setName("recruiter");
        fakePersonEntity.setRole(roleEntity);
    }

    @AfterEach
    public void tearDown() {
        personService = null;
        personRepository.delete(fakePersonEntity);
        fakePersonEntity = null;
        roleEntity = null;
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

}
