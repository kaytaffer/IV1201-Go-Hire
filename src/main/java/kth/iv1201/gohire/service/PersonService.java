package kth.iv1201.gohire.service;

import kth.iv1201.gohire.DTO.ApplicantDTO;
import kth.iv1201.gohire.DTO.ChangeApplicationStatusRequestDTO;
import kth.iv1201.gohire.DTO.CreateApplicantRequestDTO;
import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.entity.ApplicationStatusEntity;
import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.entity.RoleEntity;
import kth.iv1201.gohire.repository.ApplicationStatusRepository;
import kth.iv1201.gohire.repository.RoleRepository;
import kth.iv1201.gohire.repository.PersonRepository;
import kth.iv1201.gohire.service.exception.ApplicationHandledException;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import kth.iv1201.gohire.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Class that handles business logic related to persons.
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class PersonService {
    private final int APPLICANT_ROLE_ID = 2;
    private final int APPLICATION_STATUS_ACCEPTED = 1;
    private final int APPLICATION_STATUS_REJECTED = 2;
    private final int APPLICATION_STATUS_UNHANDLED = 3;

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final PasswordEncoder passwordEncoder;

    /**
    * Creates an instance of the <code>PersonService</code>.
    * @param personRepository The <code>PersonRepository</code> to use.
    * @param roleRepository The <code>RoleRepository</code> to use.
     * @param passwordEncoder the <code>PasswordEncoder</code> implementation to use for encoding passwords
    */
    @Autowired
    public PersonService(PersonRepository personRepository, RoleRepository roleRepository,
                         ApplicationStatusRepository applicationStatusRepository,PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.applicationStatusRepository = applicationStatusRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Fetches a user as a <code>LoggedInPersonDTO</code> by username
     * @param username the username
     * @return The <code>LoggedInPersonDTO</code>
     * @throws UserNotFoundException Thrown if no user with that name is found
     */
    public LoggedInPersonDTO fetchLoggedInPersonByUsername(String username) throws UserNotFoundException {
        PersonEntity personEntity = personRepository.findByUsername(username);
        if(personEntity == null){
            throw new UserNotFoundException("User with given username does not exist.");
        }
        return new LoggedInPersonDTO(personEntity.getId(), personEntity.getUsername(), personEntity.getRole().getName());

    }

    /**
     * Creates an account for a new user.
     * @param createUserRequestDTO The DTO containing information about the user to be created.
     * @throws UserCreationFailedException if creation of user fails
     * @return A LoggedInPersonDTO representing the newly created user account.
     */
    public LoggedInPersonDTO createApplicantAccount(CreateApplicantRequestDTO createUserRequestDTO) throws UserCreationFailedException{
        if(personRepository.existsByUsername(createUserRequestDTO.getUsername())){
            throw new UserCreationFailedException("Username" + createUserRequestDTO.getUsername() + "already exists");
        }
        PersonEntity personEntity = new PersonEntity();
        RoleEntity roleEntity = roleRepository.findRoleById(APPLICANT_ROLE_ID);
        ApplicationStatusEntity applicationStatusEntity = applicationStatusRepository.findById(APPLICATION_STATUS_UNHANDLED);

        String encodedPassword = passwordEncoder.encode(createUserRequestDTO.getPassword()); // Encodes raw password for secure storage.

        personEntity.setRole(roleEntity);
        personEntity.setName(createUserRequestDTO.getFirstName());
        personEntity.setSurname(createUserRequestDTO.getLastName());
        personEntity.setEmail(createUserRequestDTO.getEmail());
        personEntity.setPersonNumber(createUserRequestDTO.getPersonNumber());
        personEntity.setUsername(createUserRequestDTO.getUsername());
        personEntity.setPassword(encodedPassword);
        personEntity.setApplicationStatus(applicationStatusEntity);
        personEntity = personRepository.save(personEntity);
        return new LoggedInPersonDTO(personEntity.getId(), personEntity.getUsername(), personEntity.getRole().getName());
    }

    /**
     * Fetches all applicants from the database.
     * @return A list of all applicants
     */
    public List<ApplicantDTO> fetchApplicants() {
        RoleEntity roleEntity = roleRepository.findRoleById(APPLICANT_ROLE_ID);
        List<PersonEntity> persons = personRepository.findPersonEntitiesByRoleIs(roleEntity);
        List<ApplicantDTO> applicants = new LinkedList<>();
        for(PersonEntity person : persons) {
            applicants.add(new ApplicantDTO(person.getId(), person.getName(), person.getSurname(),
                    person.getApplicationStatus().getStatus()));
        }
        return applicants;
    }

    /**
     * Changes the status of an application.
     * @param request DTO containing application change request data.
     * @return the changed and saved application.
     */
    public ApplicantDTO changeApplicantStatus(ChangeApplicationStatusRequestDTO request) throws ApplicationHandledException {
        PersonEntity applicant = personRepository.findPersonById(request.getId());
        if (applicant.getApplicationStatus().getId() == APPLICATION_STATUS_UNHANDLED)
            throw new ApplicationHandledException("The application has already been handled.");
        else {
            int applicationStatus;
            if(request.getNewStatus().equals("accepted"))
                applicationStatus = APPLICATION_STATUS_ACCEPTED;
            else
                applicationStatus = APPLICATION_STATUS_REJECTED;
            applicant.setApplicationStatus(applicationStatusRepository.findById(applicationStatus));
            applicant = personRepository.save(applicant);
            return new ApplicantDTO(applicant.getId(), applicant.getName(), applicant.getSurname(), request.getNewStatus());
        }
    }

}
