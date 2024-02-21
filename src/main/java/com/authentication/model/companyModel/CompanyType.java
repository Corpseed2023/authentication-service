package com.authentication.model.companyModel;


import lombok.*;
import org.hibernate.annotations.Comment;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companyType")
public class CompanyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyTypeName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
    @Comment(value = "1 : Active, 0 : Inactive")
    private boolean isEnable;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
    @Column(name = "user_id")
    private Long userId;






}
