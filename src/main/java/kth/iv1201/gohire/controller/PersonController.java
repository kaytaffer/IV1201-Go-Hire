package kth.iv1201.gohire.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kth.iv1201.gohire.DTO.CreateApplicantRequestDTO;
import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.DTO.LoginRequestDTO;
import kth.iv1201.gohire.controller.util.Logger;
import kth.iv1201.gohire.controller.util.LoggerException;
import kth.iv1201.gohire.service.PersonService;
import kth.iv1201.gohire.controller.exception.LoginFailedException;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import kth.iv1201.gohire.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for API calls related to a <code>PersonEntity</code>
 */
@RestController
@RequestMapping("/api")
public class PersonController {

    private final PersonService personService;
    private final AuthenticationManager authenticationManager;

    /**
     * Creates a new <code>PersonController</code>
     * @param personService The <code>PersonService</code> to use
     */
    @Autowired
    public PersonController(PersonService personService, AuthenticationManager authenticationManager) {
        this.personService = personService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Handles the login API-request
     * @param loginRequest DTO containing login request data
     * @throws LoginFailedException If the username and password do not match an existing user.
     * @throws LoggerException if there is a problem with logging an event.
     * @throws UserNotFoundException If the user is authenticated but can not be fetched from the database.
     * @return <code>LoggedInPersonDTO</code> representing the logged-in user
     */
    @PostMapping("/login")
    public LoggedInPersonDTO login(@RequestBody @Valid LoginRequestDTO loginRequest, HttpSession session)
            throws LoggerException, UserNotFoundException, LoginFailedException {
        Authentication authenticationResponse = authenticateLoginRequest(loginRequest);
        saveAuthenticedUserInSession(authenticationResponse, session);
        Logger.logEvent("User logged in: " + loginRequest.getUsername());
        return personService.fetchLoggedInPersonByUsername(loginRequest.getUsername());
    }

    /**
     * Handles the create applicant API-request.
     * @param createApplicantRequest DTO containing applicant request data.
     * @return <code>LoggedInPersonDTO</code> representing the newly created and logged-in user
     * @throws UserCreationFailedException If the requested username already exists.
     * @throws LoggerException if there is a problem with logging an event.
     */
    @PostMapping("/createApplicant")
    public LoggedInPersonDTO createNewApplicant(@RequestBody @Valid CreateApplicantRequestDTO createApplicantRequest)
            throws UserCreationFailedException, LoggerException {
        LoggedInPersonDTO newApplicant = personService.createApplicantAccount(createApplicantRequest);
        Logger.logEvent("New applicant registered: " + newApplicant.getUsername());
        return newApplicant;
    }

    @GetMapping("/who")
    public String notSecret() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    /* The following methods are for testing purposes since there is no other protected content */

    @PreAuthorize("hasRole('recruiter')")
    @GetMapping("/recruiter")
    public String getRecruiterSecret() {
        return "Secret thing";
    }

    @PreAuthorize("hasRole('applicant')")
    @GetMapping("/applicant")
    public String getApplicantSecret() {
        return "Secret thing";
    }

    private Authentication authenticateLoginRequest(LoginRequestDTO loginRequest) throws LoginFailedException {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(),
                        loginRequest.getPassword());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);
        if(!authenticationResponse.isAuthenticated())
            throw new LoginFailedException("Person with given credentials does not exist.");
        return authenticationResponse;
    }

    private void saveAuthenticedUserInSession(Authentication authenticationResponse, HttpSession session) {
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }

}
