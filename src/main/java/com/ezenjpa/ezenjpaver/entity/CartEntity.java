package com.ezenjpa.ezenjpaver.entity;

import com.ezenjpa.ezenjpaver.VO.PurchaseListVO;
import com.ezenjpa.ezenjpaver.enums.Statement;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MALL_CART")
@SequenceGenerator(name = "cart_seq_generator",
                    sequenceName = "MALL_CART_SEQ",
                    allocationSize = 1)
@SqlResultSetMapping(
        name = "purchaseListForMember",
        classes =
        @ConstructorResult(
                targetClass = PurchaseListVO.class,
                columns = {
                        @ColumnResult(name = "cart_idx", type = Long.class),
                        @ColumnResult(name = "cart_list_idx", type = Long.class),
                        @ColumnResult(name = "user_idx", type = Long.class),
                        @ColumnResult(name = "cart_amount", type = Integer.class),
                        @ColumnResult(name = "option_idx", type = Integer.class),
                        @ColumnResult(name = "cart_total_price", type = Integer.class),
                        @ColumnResult(name = "cart_isdone", type = int.class),
                        @ColumnResult(name = "purchase_idx", type = Long.class),
                        @ColumnResult(name = "purchase_statement", type = String.class),
                        @ColumnResult(name = "purchase_date", type = Date.class),
                        @ColumnResult(name = "goods_idx", type = Long.class),
                        @ColumnResult(name = "goods_thumb", type = String.class),
                        @ColumnResult(name = "goods_name", type = String.class),
                        @ColumnResult(name = "goods_price", type = Integer.class)}))
@NamedNativeQuery(name = "purchaseListForMember",
        query = "SELECT c.*, p.purchase_idx, p.purchase_statement, p.purchase_date, " +
                "g.goods_idx, g.goods_thumb, g.goods_name, g.goods_price FROM " +
                "MALL_CART c INNER JOIN MALL_PURCHASE P on c.CART_LIST_IDX = P.CART_LIST_IDX " +
                "INNER JOIN MALL_GOODS G on G.GOODS_IDX = c.GOODS_IDX " +
                "WHERE CART_ISDONE = 1 AND C.USER_IDX = :userIdx",
        resultSetMapping = "purchaseListForMember")
@NamedNativeQuery(name = "purchaseListForMemberByCat",
        query = "SELECT c.*, p.purchase_idx, p.purchase_statement, p.purchase_date, " +
                "g.goods_idx, g.goods_thumb, g.goods_name, g.goods_price FROM " +
                "MALL_CART c INNER JOIN MALL_PURCHASE P on c.CART_LIST_IDX = P.CART_LIST_IDX " +
                "INNER JOIN MALL_GOODS G on G.GOODS_IDX = c.GOODS_IDX " +
                "WHERE CART_ISDONE = 1 AND C.USER_IDX = :userIdx AND p.PURCHASE_STATEMENT = :statement",
        resultSetMapping = "purchaseListForMember")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "cart_seq_generator")
    @Column(name = "CART_IDX")
    private Long cartIdx;

    @ManyToOne
    @JoinColumn(name = "CART_LIST_IDX")
    private CartListEntity cartListEntity;

    @ManyToOne
    @JoinColumn(name = "USER_IDX")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "GOODS_IDX")
    private GoodsEntity goodsEntity;

    @ManyToOne
    @JoinColumn(name = "OPTION_IDX")
    private OptionEntity optionEntity;

    @Column(name = "CART_AMOUNT")
    private Integer cartAmount;

    @Column(name = "CART_TOTAL_PRICE")
    private Integer cartTotalPrice;

    @Column(name = "CART_ISDONE")
    private Integer cartIsDone;


}
