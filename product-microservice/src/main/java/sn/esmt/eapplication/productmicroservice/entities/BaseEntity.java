package sn.esmt.eapplication.productmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sn.esmt.eapplication.productmicroservice.utils.CustomInstantDeSerializer;
import sn.esmt.eapplication.productmicroservice.utils.CustomInstantSerializer;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = CustomInstantDeSerializer.class)
    private Instant createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonIgnore
    private Instant updatedAt;

    @JsonIgnore
    private boolean deleted;

    @Column(unique = true)
    private String slug;

    @JsonGetter
    @JsonSerialize(using = CustomInstantSerializer.class)
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getSlug() {
        return slug;
    }

    @PrePersist
    public void generateSlug() {
        this.slug = UUID.randomUUID().toString();
    }
}
