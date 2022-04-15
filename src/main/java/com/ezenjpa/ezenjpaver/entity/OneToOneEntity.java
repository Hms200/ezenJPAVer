package com.ezenjpa.ezenjpaver.entity;

import com.ezenjpa.ezenjpaver.DTO.OneToOneDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MALL_ONETOONE")
@SequenceGenerator(name = "oneToone_seq_generator",
                    sequenceName = "MALL_ONETOONE_SEQ",
                    allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OneToOneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "oneToone_seq_generator")
    @Column(name = "ONETOONE_IDX")
    private Long oneToOneIdx;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "USER_IDX")
    private UserEntity userEntity;

    @Column(name = "ONETOONE_CAT")
    private String oneToOneCat;

    @Column(name = "ONETOONE_TITLE")
    private String oneToOneTitle;

    @Column(name = "ONETOONE_CONTENTS")
    private String oneToOneContents;

    @Column(name = "ONETOONE_DATE")
    @Temporal(TemporalType.DATE)
    private Date oneToOneDate;

    @Column(name = "ONETOONE_ISREPLIED")
    private Integer ontToOneIsReplied;

    @Column(name = "ONETOONE_REPLY")
    private String oneToOneReply;

    @Temporal(TemporalType.DATE)
    @Column(name = "ONETOONE_REPLY_DATE")
    private Date oneToOneReplyDate;

    public OneToOneDTO convertToOneToOneDTO(OneToOneEntity oneToOne){
        return OneToOneDTO.builder()
                .onetooneIdx(oneToOne.getOneToOneIdx())
                .userIdx(oneToOne.getUserEntity().getUserIdx())
                .onetooneCat(oneToOne.getOneToOneCat())
                .onetooneTitle(oneToOne.getOneToOneTitle())
                .onetooneContents(oneToOne.getOneToOneContents())
                .onetooneDate(oneToOne.getOneToOneDate())
                .onetooneIsreplied(oneToOne.getOntToOneIsReplied())
                .onetooneReply(oneToOne.getOneToOneReply())
                .onetooneReplyDate(oneToOne.getOneToOneReplyDate())
                .build();
    }

}
