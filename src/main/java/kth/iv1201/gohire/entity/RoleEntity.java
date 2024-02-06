package kth.iv1201.gohire.entity;
import jakarta.persistence.*;

import java.util.Set;

/**
 * JPA Entity representing a Person's role
 */
@Entity
@Table(name="role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    Integer id;
    @Column(length = 255)
    String name;

    @OneToMany(mappedBy = "role")
    private Set<PersonEntity> persons;
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
}
