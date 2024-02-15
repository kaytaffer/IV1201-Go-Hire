package kth.iv1201.gohire.service;

import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class SpringDataJpaUserDetailsService implements UserDetailsService {

    private final PersonRepository repository;

    @Autowired
    public SpringDataJpaUserDetailsService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        PersonEntity person = this.repository.findByUsername(name);
        return User.withDefaultPasswordEncoder()
                .username(person.getUsername())
                .password(person.getPassword())
                .roles(person.getRole().getName())
                .build();
    }

}