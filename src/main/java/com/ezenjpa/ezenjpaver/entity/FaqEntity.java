package com.ezenjpa.ezenjpaver.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "MALL_FAQ")
@SequenceGenerator(name = "faq_seq_generator",
                    sequenceName = "MALL_FAQ_SEQ",
                    allocationSize = 1)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FaqEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "faq_seq_generator")
    @Column(name = "FAQ_IDX")
    private Long faqIdx;

    @Column(name = "FAQ_TITLE")
    private String faqTitle;

    @Column(name = "FAQ_CONTENTS")
    private String faqContents;

    @Column(name = "FAQ_CAT")
    private String faqCat;     //   enum으로 변경할 것

}
