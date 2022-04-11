package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.DTO.ReviewDTO;
import com.ezenjpa.ezenjpaver.entity.ReviewEntity;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    //모든 리뷰 가져옴
    @Query(name = "reviews", nativeQuery = true)
    List<ReviewDTO> reviews();
    //최신 리뷰 10개 가져옴
    @Query(name = "recentReviews", nativeQuery = true)
    List<ReviewDTO> recentReviews();

}