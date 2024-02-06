package kth.iv1201.gohire.controller;

import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.DTO.LoginRequestDTO;
import kth.iv1201.gohire.service.PersonService;
import kth.iv1201.gohire.service.exception.LoginFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for API calls related to a <code>PersonEntity</code>
 */
@RestController
@RequestMapping("/api")
public class PersonController {

    private final PersonService personService;

    /**
     * Creates a new <code>PersonController</code>
     * @param personService The <code>PersonService</code> to use
     */
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // TODO test
    /**
     * Handles the login API-request
     * @param loginRequest DTO containing login request data
     * @return <code>LoggedInPersonDTO</code> representing the logged-in user
     */
    @PostMapping("/login")
    public LoggedInPersonDTO login(@RequestBody LoginRequestDTO loginRequest) throws LoginFailedException {
        // TODO validate
        // TODO transaction
        return personService.login(loginRequest);
    }

}
