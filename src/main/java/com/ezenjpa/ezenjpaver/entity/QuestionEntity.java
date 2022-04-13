package com.ezenjpa.ezenjpaver.entity;

import lombok.*;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MALL_QUESTION")
@SequenceGenerator(name = "question_seq_generator",
                    sequenceName = "MALL_QUESTION_SEQ",
                    allocationSize = 1)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "question_seq_generator")
    @Column(name = "QUESTION_IDX")
    private Long questionIdx;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "USER_IDX")
    private UserEntity userEntity;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "GOODS_IDX")
    private GoodsEntity goodsEntity;

    @Column(name = "QUESTION_TITLE")
    private String questionTitle;

    @Column(name = "QUESTION_CONTENTS")
    private String questionContents;

    @Column(name = "QUESTION_DATE")
    @Temporal(TemporalType.DATE)
    private Date questionDate;

    @Column(name = "QUESTION_ISREPLIED")
    private Integer questionIsReplied;

    @Column(name = "QUESTION_REPLY")
    private String questionReply;

    @Column(name = "QUESTION_REPLY_DATE")
    @Temporal(TemporalType.DATE)
    private Date questionReplyDate;



}
