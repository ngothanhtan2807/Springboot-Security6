package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
@Data
@Getter
@Setter
@MappedSuperclass
public abstract class CommonEntity {
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false)
    private User createdBy;
    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @CreatedBy
    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "dd:MM:yyyy")
    private LocalDate createdAt;
    @LastModifiedDate
    @Column(name ="updated_at")
    @JsonFormat(pattern = "dd:MM:yyyy")
    private LocalDate updatedAt;
}
