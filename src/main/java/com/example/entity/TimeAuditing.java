package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Data
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeAuditing {
//    @CreatedBy
//    @ManyToOne
//    @JoinColumn(name = "created_by", updatable = false)
//    private User createdBy;
//    @LastModifiedBy
//    @ManyToOne
//    @JoinColumn(name = "updated_by")
//    private User updatedBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "dd:MM:yyyy")
    private LocalDate createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd:MM:yyyy")
    private LocalDate updatedAt;
}
