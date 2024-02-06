package kth.iv1201.gohire.service;

import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.service.exception.LoginFailedException;
import kth.iv1201.gohire.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Class that handles business logic related to persons.
 */
@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Fetches a <code>LoggedInPersonDTO</code> matching the credentials entered by the user.
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return <code>LoggedInPersonDTO</code> representing the logged-in user matching the entered credentials.
     * @throws LoginFailedException when a matching user does not exist in the database.
     */
    public LoggedInPersonDTO login(String username, String password) throws LoginFailedException {
        PersonEntity personEntity = personRepository.findByUsernameAndPassword(username, password);
        if(personEntity == null){
            throw new LoginFailedException("Person with given credentials does not exist");
        }
        return new LoggedInPersonDTO(personEntity.getId(), personEntity.getUsername(), personEntity.getRole().getName());
    }


}
