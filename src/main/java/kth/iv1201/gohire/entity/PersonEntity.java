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
    Integer id;

    @Column(length = 255)
    String name;
    @Column(length = 255)
    String surname;
    @Column(length = 255, name = "pnr")
    String personNumber;

    @Column(length = 255)
    String email;

    @Column(length = 255)
    String password;

    @ManyToOne(fetch=FetchType.EAGER) //TODO optional = false ?, Should a person be able to have a null value in 'role_id'
    RoleEntity roleId;

    @Column(length = 255)
    String username;

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

    public RoleEntity getRoleId() {
        return roleId;
    }

    public void setRoleId(RoleEntity roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
