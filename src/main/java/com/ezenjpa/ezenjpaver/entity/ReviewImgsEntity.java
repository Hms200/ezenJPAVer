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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "review_idx")
    private ReviewEntity reviewEntity;

}
