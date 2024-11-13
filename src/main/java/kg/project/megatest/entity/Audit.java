package kg.project.megatest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Audit<U> {
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    U createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Bishkek")
    Date creationDate;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    U lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Bishkek")
    Date lastModifiedDate;

    @Column(name = "deleted_date")
    Date deletedDate;

    @Column(name = "deleted_by")
    U deletedBy;
    boolean deleted = Boolean.FALSE;
}
