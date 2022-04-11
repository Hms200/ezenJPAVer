package com.ezenjpa.ezenjpaver.entity;

import lombok.*;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;

@Entity
@Table(name = "MALL_REVIEW_IMGS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewImgsEntity {

    @Id
    @Column(name = "REVIEW_IMG")
    private String reviewImg;

    @OneToOne
    @JoinColumn(name = "REVIEW_IDX")
    private ReviewEntity reviewEntity;

}
