package com.ezenjpa.ezenjpaver.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "MALL_GOODS_OPTION")
@SequenceGenerator(name = "option_seq_generator",
                    sequenceName = "MALL_GOODS_OPTION_SEQ",
                    allocationSize = 1)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "option_seq_generator")
    @Column(name = "OPTION_IDX")
    private Long optionIdx;

    @Column(name = "OPTION_NAME")
    private String optionName;

    @Column(name = "OPTION_PRICE")
    private Integer optionPrice;

}
