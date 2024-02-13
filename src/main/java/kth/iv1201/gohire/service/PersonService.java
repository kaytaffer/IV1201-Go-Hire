package kth.iv1201.gohire.service;

import kth.iv1201.gohire.DTO.CreateUserRequestDTO;
import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.DTO.LoginRequestDTO;
import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.entity.RoleEntity;
import kth.iv1201.gohire.repository.RoleRepository;
import kth.iv1201.gohire.service.exception.LoginFailedException;
import kth.iv1201.gohire.repository.PersonRepository;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Class that handles business logic related to persons.
 */
@Service
public class PersonService {
    private final int APPLICANTROLEID = 2;

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Fetches a <code>LoggedInPersonDTO</code> matching the credentials entered by the user.
     * @param loginRequest DTO containing the username and password entered by the user.
     * @return <code>LoggedInPersonDTO</code> representing the logged-in user matching the entered credentials.
     * @throws LoginFailedException when a matching user does not exist in the database.
     */
    public LoggedInPersonDTO login(LoginRequestDTO loginRequest) throws LoginFailedException {
        PersonEntity personEntity = personRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if(personEntity == null){
            throw new LoginFailedException("Person with given credentials does not exist");
        }
        return new LoggedInPersonDTO(personEntity.getId(), personEntity.getUsername(), personEntity.getRole().getName());
    }

    /**
     * Creates an account for a new user.
     * @param createUserRequestDTO The DTO containing information about the user to be created.
     * @return A LoggedInPersonDTO representing the newly created user account.
     */
    public LoggedInPersonDTO createAccount(CreateUserRequestDTO createUserRequestDTO) throws UserCreationFailedException{
        if(personRepository.existsByUsername(createUserRequestDTO.getUsername())){
            throw new UserCreationFailedException("Username" + createUserRequestDTO.getUsername() + "already exists");
        }
        PersonEntity personEntity = new PersonEntity();
        RoleEntity roleEntity = roleRepository.findRoleById(APPLICANTROLEID);

        personEntity.setRole(roleEntity);
        personEntity.setName(createUserRequestDTO.getFirstName());
        personEntity.setSurname(createUserRequestDTO.getLastName());
        personEntity.setEmail(createUserRequestDTO.getEmail());
        personEntity.setPersonNumber(createUserRequestDTO.getPersonNumber());
        personEntity.setUsername(createUserRequestDTO.getUsername());
        personEntity.setPassword(createUserRequestDTO.getPassword());
        personEntity = personRepository.save(personEntity);
        return new LoggedInPersonDTO(personEntity.getId(), personEntity.getUsername(), personEntity.getRole().getName());
    }

}
