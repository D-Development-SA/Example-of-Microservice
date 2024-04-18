package org.example.waste_manager_service.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="waste_manager")
public class WasteManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String nif;

    @JsonIgnore
    @Column(nullable = false, unique = true)
    private long idWasteManagerAddressEntity;

    @Column(nullable = false)
    private boolean isEnabled;

    @NotNull
    @Positive
    @Column(nullable = false, unique = true)
    private long version;

    @Column(nullable = false, unique = true)
    private Date createDate;

    @Column(nullable = false, unique = true)
    private Date lastModifiedDate;

    @Transient
    private WasteManagerAddressEntity wasteManagerAddressEntity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "waste_manager_entity_id")
    private Set<WasteCenterAuthorizationEntity> wasteCenterAuthorizationEntities = new LinkedHashSet<>();

    @PrePersist
    private void prePersist() {
        createDate = new Date();
        lastModifiedDate = new Date();
    }

    @PreUpdate
    private void preUpdate(){
        lastModifiedDate = new Date();
    }
}
