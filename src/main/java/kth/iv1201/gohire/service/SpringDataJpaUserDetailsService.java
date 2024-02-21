package kth.iv1201.gohire.service;

import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used by AuthenticationManager for fetching user information when authenticating.
 */
@Component
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class SpringDataJpaUserDetailsService implements UserDetailsService {

    private final PersonRepository repository;

    /**
     * Creates a new instance
     * @param repository the personRepository for fetching users.
     */
    @Autowired
    public SpringDataJpaUserDetailsService(PersonRepository repository) {
        this.repository = repository;
    }

    /**
     * Tells spring how to find a user
     * @param name name of user
     * @return User details
     * @throws UsernameNotFoundException is thrown when fetching a user that does not exist.
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        PersonEntity person = this.repository.findByUsername(name);
        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.withDefaultPasswordEncoder()
                .username(person.getUsername())
                .password(person.getPassword())
                .roles(person.getRole().getName())
                .build();
    }
}