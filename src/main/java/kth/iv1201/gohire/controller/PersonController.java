package kth.iv1201.gohire.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kth.iv1201.gohire.DTO.CreateApplicantRequestDTO;
import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.DTO.LoginRequestDTO;
import kth.iv1201.gohire.service.PersonService;
import kth.iv1201.gohire.service.exception.LoginFailedException;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     * @return <code>LoggedInPersonDTO</code> representing the logged-in user
     */
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public LoggedInPersonDTO login(@RequestBody @Valid LoginRequestDTO loginRequest, HttpSession session)
            throws LoginFailedException {

        LoggedInPersonDTO user = personService.login(loginRequest);

        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);
        System.out.println(authenticationResponse.getName());
        System.out.println(authenticationResponse.isAuthenticated());

        if(authenticationResponse.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
            // setting role to the session
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            return user;
        }
        else
            return null;

        /*Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user2 = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user2.getUsername();
        System.out.println(name);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());

        System.out.printf("%s logged in with role %s\n", user.getUsername(), user.getRole());
        return user;*/
    }

    /**
     * Handles the create applicant API-request.
     * @param createApplicantRequest DTO containing applicant request data.
     * @return <code>LoggedInPersonDTO</code> representing the newly created and logged-in user
     * @throws UserCreationFailedException If the requested username already exists.
     */
    @PostMapping("/createApplicant")
    public LoggedInPersonDTO createNewApplicant(@RequestBody @Valid CreateApplicantRequestDTO createApplicantRequest)
            throws UserCreationFailedException {
        return personService.createApplicantAccount(createApplicantRequest);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/who")
    public String notSecret() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @PreAuthorize("hasRole('recruiter')")
    @GetMapping("/secret")
    public String getSecret() {
        return "Secret thing";
    }

}
