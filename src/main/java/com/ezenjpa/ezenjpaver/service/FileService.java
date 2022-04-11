package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.enums.ImgCat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Service
@Slf4j
public class FileService {

    // 상품이미지 업로드 경로
    @Value("${app.goods.img.dir}")
    private String goodsImgUploadDir;
    // 리뷰이미지 업로드 경로
    @Value("${app.reviews.img.dir}")
    private String reviewImgUploadDir;
    // thumbnail 이미지 업로드 경로
    @Value("${app.thumb.img.dir}")
    private String thumbImgUploadDir;
    // detail 이미지 업로드 경로
    @Value("${app.goods.detail.dir}")
    private String detailImgUploadDir;

    public String fileUploader(ImgCat cat, MultipartFile file) throws Exception{

        if(file.getOriginalFilename().isEmpty() || file.getOriginalFilename().isBlank()) {
            log.info("지정된 파일이 없습니다.");
            throw new Exception("파일이 없습니다.");
        }
        String uploadDir = "";
        switch (cat){
            case GOODS: uploadDir = goodsImgUploadDir; break;
            case THUMB: uploadDir = thumbImgUploadDir; break;
            case DETAIL: uploadDir = detailImgUploadDir; break;
            case REVIEWS: uploadDir = reviewImgUploadDir; break;
            default: throw new Exception("잘못된 카테고리 지정");
        }
        // os에 따른 dir 구문문자 처리
        uploadDir = uploadDir.replace("/", File.separator);
        // 파일명 생성기
        String newName = fileNameGenerator(file.getOriginalFilename());
        // 파일 저장 경로
        Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(newName));
        // input stream 으로 파일 가져와서 copyLocation에 저장. 기존에 존재하는 파일이면 replace
        try {
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("업로드 실패 파일: "+file.getOriginalFilename());
        }
        // dir 구분문자를 /로 교체
        return copyLocation.toString().replace(File.separator, "/").replace("src/main/resources/static/img/", "/img/");
    }

    public String fileNameGenerator(String originalFileName){
        Random random = new Random();
        String randomNum = String.valueOf(random.nextInt(10000));
        originalFileName = originalFileName.replaceAll("-", "_");
        return randomNum + originalFileName;
    }


}
