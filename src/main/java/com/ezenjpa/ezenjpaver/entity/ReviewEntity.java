package com.ezenjpa.ezenjpaver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MALL_REVIEW")
@SequenceGenerator(name = "review_seq_generator",
                    sequenceName = "MALL_REVIEW_SEQ",
                    allocationSize = 1)
@NamedNativeQuery(name = "reviews",
        query = "SELECT r.*, g.GOODS_NAME, i.REVIEW_IMG FROM MALL_REVIEW r " +
                "INNER JOIN MALL_GOODS g " +
                "ON r.GOODS_IDX  = g.GOODS_IDX " +
                "LEFT OUTER JOIN MALL_REVIEW_IMGS i " +
                "ON r.REVIEW_IDX = i.REVIEW_IDX " +
                "WHERE g.GOODS_ONSALE = 1",
        resultSetMapping = "reviews")
@NamedNativeQuery(name = "recentReviews",
        query = "SELECT * FROM(" +
                "SELECT r.*, g.GOODS_NAME, i.REVIEW_IMG, rank() over (order by r.REVIEW_IDX desc )as rnk FROM MALL_REVIEW r " +
                "INNER JOIN MALL_GOODS g " +
                "ON r.GOODS_IDX  = g.GOODS_IDX " +
                "LEFT OUTER JOIN MALL_REVIEW_IMGS i " +
                "ON r.REVIEW_IDX = i.REVIEW_IDX " +
                "WHERE g.GOODS_ONSALE = 1) " +
                "WHERE rnk < 11",
        resultSetMapping = "reviews")
@SqlResultSetMapping(
        name="reviews",
        classes={
                @ConstructorResult(
                        targetClass=com.ezenjpa.ezenjpaver.DTO.ReviewDTO.class,
                        columns={
                                @ColumnResult(name = "review_idx", type=Long.class),
                                @ColumnResult(name = "user_idx", type=Long.class),
                                @ColumnResult(name = "goods_idx", type = Long.class),
                                @ColumnResult(name = "review_contents", type = String.class),
                                @ColumnResult(name = "review_star", type = int.class),
                                @ColumnResult(name = "review_isReplied", type = int.class),
                                @ColumnResult(name = "review_reply", type = String.class),
                                @ColumnResult(name = "review_date", type = Date.class),
                                @ColumnResult(name = "review_reply_date", type = Date.class),
                                @ColumnResult(name = "goods_name", type = String.class),
                                @ColumnResult(name = "review_img", type = String.class)})})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "review_seq_generator")
    @Column(name = "REVIEW_IDX")
    private Long reviewIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_IDX")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GOODS_IDX")
    private GoodsEntity goodsEntity;

    @Column(name = "REVIEW_CONTENTS")
    private String reviewContents;

    @Column(name = "REVIEW_STAR")
    private Integer reviewStar;

    @Column(name = "REVIEW_ISREPLIED")
    private Integer reviewIsReplied;

    @Column(name = "REVIEW_REPLY")
    private String reviewReply;

    @Column(name = "REVIEW_DATE")
    @Temporal(TemporalType.DATE)
    private Date reivewDate;

    @Column(name = "REVIEW_REPLY_DATE")
    @Temporal(TemporalType.DATE)
    private Date reviewReplyDate;

}
