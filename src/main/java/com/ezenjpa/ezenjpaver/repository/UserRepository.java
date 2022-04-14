package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.VO.UserListForAdmin;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import net.bytebuddy.TypeCache;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity getByUserIdx(Long userIdx);
    // id로 찾기
    UserEntity findByUserId(String userID);
    // id와 email로 찾기
    UserEntity findByUserNameAndUserEmail(String userName, String userEmail);
    // id 이름 email로 찾기
    UserEntity findByUserIdAndUserNameAndUserEmail(String userId, String userName, String userEmail);

    // admin 회원관리
    @Query("select new com.ezenjpa.ezenjpaver.VO.UserListForAdmin(" +
            "u.userIdx, u.userId, u.userName, sum(c.cartAmount) as totalAmount, " +
            "sum(p.totalPrice) as totalPrice, u.joinDate) " +
            "from UserEntity u left outer join PurchaseEntity p on u.userIdx = p.userEntity.userIdx " +
            "left outer join CartEntity c on u.userIdx = c.userEntity.userIdx and c.cartIsDone = 1 " +
            "group by u.userIdx, u.userId, u.userName, u.joinDate")
    List<UserListForAdmin> getAllForAdmin();

    @Query("select new com.ezenjpa.ezenjpaver.VO.UserListForAdmin(" +
            "u.userIdx, u.userId, u.userName, sum(c.cartAmount) as totalAmount, " +
            "sum(p.totalPrice) as totalPrice, u.joinDate) " +
            "from UserEntity u left outer join PurchaseEntity p on u.userIdx = p.userEntity.userIdx " +
            "left outer join CartEntity c on u.userIdx = c.userEntity.userIdx and c.cartIsDone = 1 " +
            "where u.userId like :searchText " +
            "group by u.userIdx, u.userId, u.userName, u.joinDate")
    List<UserListForAdmin> getUserListsForAdminBySearching(@Param("searchText") String searchText);

    @Query("select new com.ezenjpa.ezenjpaver.VO.UserListForAdmin(" +
            "u.userIdx, u.userId, u.userName, sum(c.cartAmount) as totalAmount, " +
            "sum(p.totalPrice) as totalPrice, u.joinDate) " +
            "from UserEntity u left outer join PurchaseEntity p on u.userIdx = p.userEntity.userIdx " +
            "left outer join CartEntity c on u.userIdx = c.userEntity.userIdx and c.cartIsDone = 1 " +
            "group by u.userIdx, u.userId, u.userName, u.joinDate")
    List<UserListForAdmin> getUserListsForAdminByFilter(Sort sort);
}