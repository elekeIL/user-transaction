package com.transaction.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;


@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(
            generator = "id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "id"
    )
    protected Long id;
    @Basic
    @Column(
            nullable = false
    )
    protected LocalDateTime createdAt;
    @Basic
    protected LocalDateTime lastUpdatedAt;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Users createdBy;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Users lastUpdatedBy;

    public BaseEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Optional<LocalDateTime> getLastUpdatedAt() {
        return Optional.ofNullable(this.lastUpdatedAt);
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Optional<Users> getCreatedBy() {
        return Optional.ofNullable(this.createdBy);
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Optional<Users> getLastUpdatedBy() {
        return Optional.ofNullable(this.lastUpdatedBy);
    }

    public void setLastUpdatedBy(Users lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

}
