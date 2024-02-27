package kth.iv1201.gohire.entity;

import jakarta.persistence.*;

/**
 * JPA entity representing an application status
 */
@Entity
@Table(name = "application_status")
public class ApplicationStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="application_status_id")
    private  Integer id;

    @Column(length =  255)
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
