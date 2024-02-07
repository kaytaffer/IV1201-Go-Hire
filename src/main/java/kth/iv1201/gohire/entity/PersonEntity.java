package kth.iv1201.gohire.entity;

import jakarta.persistence.*;

/**
 * JPA Entity representing a Person
 */
@Entity
@Table(name = "person")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer id;

    @Column(length = 255)
    private String name;
    
    @Column(length = 255)
    private String surname;
    
    @Column(length = 255, name = "pnr")
    private String personNumber;

    @Column(length = 255)
    private String email;

    @Column(length = 255)
    private String password;

   @ManyToOne(fetch=FetchType.EAGER) //TODO optional = false ?, Should a person be able to have a null value in 'role_id'
   @JoinColumn(name = "role_id", referencedColumnName = "role_id")
   private RoleEntity role;

    @Column(length = 255)
    private String username;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getPersonNumber() {
        return personNumber;
    }
    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
