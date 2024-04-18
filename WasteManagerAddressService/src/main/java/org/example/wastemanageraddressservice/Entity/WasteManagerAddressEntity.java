package org.example.wastemanageraddressservice.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "WasteManagerAddress")
public class WasteManagerAddressEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    private boolean isEnabled;

    @Positive
    @Column(nullable = false, unique = true)
    private long version;

    @Column(nullable = false, unique = true)
    private Date createdDate;
    @Column(nullable = false, unique = true)
    private Date lastModifiedDate;

    @PrePersist
    public void prePersist() {
        createdDate = new Date();
        lastModifiedDate = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedDate = new Date();
    }

}
