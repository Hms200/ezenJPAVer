package com.ezenjpa.ezenjpaver.service;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class PagenationService {

    private int number;    //현재 페이지
    private int totalPages;
    private Long totalElements;
    private boolean hasPreviousPage;
    private boolean isFirstPage;
    private boolean hasNextPage;
    private boolean isLastPage;
    private int beginPage;
    private int endPage;

    // numberOfPagenation - 페이지 표시기에 표시될 최대 페이지 수
    public PagenationService pagenationInfo(Page<?> page, int numberOfPagenation){
        int currentPage = page.getNumber()+1;
        // pagenation 시작 값
        int beginPagenation = currentPage%numberOfPagenation == 0 ?
                currentPage-numberOfPagenation +1 : (currentPage/numberOfPagenation)*numberOfPagenation+1;
        // pagenation 끝 값
        int endPagenation;
        if(currentPage%numberOfPagenation == 0) {
            endPagenation = currentPage;
        }else if(currentPage/numberOfPagenation == page.getTotalPages()/numberOfPagenation) {
            endPagenation = page.getTotalPages();
        }else {
            endPagenation = ((currentPage/numberOfPagenation)+1)*numberOfPagenation;
        }

        return PagenationService.builder()
                .number(currentPage)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .hasPreviousPage(page.hasPrevious())
                .isFirstPage(page.isFirst())
                .hasNextPage(page.hasNext())
                .isLastPage(page.isLast())
                .beginPage(beginPagenation)
                .endPage(endPagenation)
                .build();
    }

}
