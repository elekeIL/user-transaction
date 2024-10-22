package com.transaction.entity;

import com.transaction.enums.GenericStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Optional;

@MappedSuperclass
@Data
public class StatusEntity extends BaseEntity {
    @Basic(
            optional = false
    )
    @Column(
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    protected GenericStatus status;
    @Basic
    private String reasonForStatusChange;

    public StatusEntity() {
    }

    public GenericStatus getStatus() {
        return this.status;
    }

    public void setStatus(GenericStatus status) {
        this.status = status;
    }

    public Optional<String> getReasonForStatusChange() {
        return Optional.ofNullable(this.reasonForStatusChange);
    }

    public void setReasonForStatusChange(String reasonForStatusChange) {
        this.reasonForStatusChange = reasonForStatusChange;
    }
}

