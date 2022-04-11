<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/custom.css">
</head>
<body>
<c:import url="pcMain.jsp"></c:import>


<div  class="container-sm container-fluid d-flex flex-column align-items-center pl-0 pr-0 position-relative"  style="max-width: 520px;max-height: 100vh; overflow: auto;" >


<c:import url="header.jsp"></c:import>

 <!-- container -->
    <div class="container-sm container-fluid d-flex flex-column justify-content-center align-items-center position-relative px-0 mb-5" id="mainContainer" style="max-width: 520px; margin-top: 60px">
   
        <!-- title container -->
        <div class="container-fluid d-flex flex-row mb-4 " style="height: 60px;">
            <!-- title -->
                <div class="col-12 my-4 py-2 text-start font-weight-bold text-black-50">
                  공지사항
                </div>
            </div>
        <!-- table container-->
        <div class="container-fluid text-center">
            <!-- table -->
            <table class="table table-hover border border-dark-50">
                <thead class="thead-dark-50 bg-secondary">
                    <th scope="col" class="px-0">번호</th>
                    <th scope="col">제목</th>
                    <th scope="col">날짜</th>
                </thead>
                <tbody>
                 	<c:forEach var="notice" items="${ noticeList }">
                    <tr>
                        <th scope="row">${ notice.noticeIdx }</th>
                        <td onclick="popupHideAndShow(target = 'notice_popup${ notice.noticeIdx}')" style="cursor: pointer;"> ${ notice.noticeTitle }</td>
                        <fmt:formatDate var="date" value="${ notice.noticeDate }" pattern="YY.MM.dd"/>
                        <td>${ date } </td>
                    </tr>
                    </c:forEach>
                    
                </tbody>
            </table>
            
        </div>
            
        <div class="d-flex flex-column justify-content-center my-3">
        	<!-- 작성버튼 admin에게만 보임 -->
        	<c:if test="${ userId eq 'admin' }">
            <button class="btn btn-secondary text-dark border-0 mx-auto font-weight-bold" style="width: 100px;" onclick="popupHideAndShow(target = 'notice_write_popup')">
                작성
            </button>
			</c:if>
            <!-- 페이지 표시기 -->
			
		    <div class="container row my-3 mx-auto">
		      <nav class="mx-auto">
		        <ul class="pagination justify-content-center ">
					<!-- 현재 페이지가 페이지표시기의 페이지 표시 수 보다 작으면 뒤로가기버튼 disable -->
		          <li class="page-item <c:if test="${ pages.number <= 5 || pages.isFirstPage == true}">disabled</c:if>">
		            <a class="page-link" href="notice?page=${ pages.beginPage - 2}&size=10">&lang;</a>
		          </li>
				  <c:forEach var="page" begin="${ pages.beginPage }" end="${ pages.endPage }" step="1">
		          <li class="page-item <c:if test="${ pages.number == page }">active</c:if>">
		            <a class="page-link" href="notice?page=${ page-1 }&size=10">${ page }</a>
		          </li>
		          </c:forEach>
		          <!-- 마지막페이지까지 표시되면 앞으로 가기 표시 안됨 -->
		          <li class="page-item <c:if test="${ pages.totalPages+1 eq pages.endPage || pages.hasNextPage == false }">disabled</c:if>">
		            <a class="page-link" href="notice?page=${ pages.endPage}&size=10">&rang;</a>
		          </li>
		        </ul>
		      </nav>
		    </div>
		    
		    
			    
        </div>
        
        
        <!-- 공지사항 열람/수정(관리자) 팝업창 -->
        <c:forEach var="notice" items="${ noticeList }">
        <div class="position-absolute d-none bg-white border border-dark-50 rouned" id="notice_popup${ notice.noticeIdx }" style=" top: 120px;z-index: 1100;">

            <!-- 닫기 버튼 -->
            <div class="w-100 d-flex flex-row my-2 pr-3 justify-content-end">
                <img src="/img/icon/cross.png" alt="닫기버튼" width="25px" height="25px" onclick="popupHideAndShow(target ='notice_popup${ notice.noticeIdx}')" style="cursor: pointer;">
            </div>
            <!-- form 시작 -->
            <div class="form-group w-100">
                <form name="noticeUpdateForm${ notice.noticeIdx }" method="post" action="noticeUpdateAction" onsubmit="return changeValueOfCheckbox();">
                    <!-- 히든 input -->
                    <input type="hidden" name="noticeIdx" value="${ notice.noticeIdx }">
                    <!-- 날짜 표시 -->
                    <div class="w-100 my-1">
                    	<fmt:formatDate var="date" value="${ notice.noticeDate }" pattern="YY.MM.dd"/>
                        <input class="form-control-plaintext text-right pr-4 font-primary" disabled type="text" value="${ date }">
                    </div>
                    <!-- 공지사항 제목 -->
                    <div class="col-11 mx-auto">
                        <input class="form-control bg-white nullcheck"<c:if test="${ userId != 'admin' }"> readonly </c:if>type="text" name="noticeTitle" value="${ notice.noticeTitle }">
                    </div>
                    <!-- 공지사항 내용 -->
                    <div class="col-11 mx-auto mt-1">
                        <textarea class="form-control overflow-auto bg-white nullcheck" <c:if test="${ userId != 'admin' }"> readonly </c:if> name="noticeContents"cols="30" rows="10" style="resize: none;">${ notice.noticeContents }</textarea>
                    </div>
                    <!-- 중요공지사항(관리자) -->
                    <c:if test="${ userId == 'amdin' }">
                    <div class="ml-4 my-2 pl-2">
                        <label><input class="mr-2 checkbox" type="checkbox" name="noticeShow"<c:if test="${ userId != 'admin' }"> disabled </c:if><c:if test="${ notice.noticeShow eq 1 }"> checked</c:if>
                        	>중요공지사항</label>
                    </div>
                    </c:if>
                    <!-- 확인버튼 / 수정버튼(관리자) // 삭제버튼-->
                    <div class="mx-auto my-3" style="width: fit-content;">
                        <!-- 사용자용 확인버튼 -->
                        <button class="btn btn-dark btn-lg font-primary <c:if test="${ userId eq 'admin' }">d-none</c:if>>" style="width: 80px;" onclick="popupHideAndShow(target = 'notice_popup${ notice.noticeIdx}')" type="button">확인</button>
                        <!-- 관리자용 수정버튼 -->
                        <c:if test="${ userId eq 'admin' }">
                        <input type="submit" class="btn btn-dark btn-lg font-primary" style="width: 80px;" value="수정">
                        <button class="btn btn-secondary font-primary" style="width: 80px;" onclick="multiSubmit(formName = 'noticeUpdateForm', formAction = 'noticeDeleteAction?${ notice.noticeIdx}')">삭제</button>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
        </c:forEach>
    
        <!-- 공지사항 작성 팝업(관리자) -->
        <div class="position-absolute d-none bg-white border border-dark-50 rouned" id="notice_write_popup" style=" top: 120px;z-index: 1100;">

            <!-- 닫기 버튼 -->
            <div class="w-100 d-flex flex-row my-2 pr-3 justify-content-end">
                <img src="/img/icon/cross.png" alt="닫기버튼" width="25px" height="25px" onclick="popupHideAndShow(target ='notice_write_popup')" style="cursor: pointer;">
            </div>
            <!-- form 시작 -->
            <div class="form-group w-100">
                <form name="noticeWriteForm" method="post" action="noticeWriteAction" onsubmit="return changeValueOfCheckbox();">

                    <!-- 공지사항 제목 -->
                    <div class="col-11 mx-auto">
                        <input class="form-control bg-white nullcheck" type="text" name="noticeTitle" placeholder="제목을 입력해주세요.">
                    </div>
                    <!-- 공지사항 내용 -->
                    <div class="col-11 mx-auto mt-1">
                        <textarea class="form-control overflow-auto bg-white nullcheck" name="noticeContents"cols="30" rows="10" placeholder="내용을 입력해 주세요." style="resize: none;"></textarea>
                    </div>
                    <!-- 중요공지사항(관리자) -->
                    <div class="ml-4 my-2 pl-2">
                        <label><input class="mr-2 checkbox" type="checkbox" name="noticeShow">중요공지사항</label>
                    </div>
                    <!-- 작성버튼 -->
                    <div class="mx-auto" style="width: fit-content;">
                        <input type="submit" class="btn btn-dark btn-lg" style="width: 100px;" value="작성">
                    </div>
                </form>
            </div>
        </div>
    </div>

    
<c:import url="footer.jsp"></c:import>
<c:import url="nav.jsp"></c:import>

</div>

<!-- bootstrap js  // jquery js는 nav에 들어있는채로 import-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF" crossorigin="anonymous"></script>
<script src="/js/main.js"></script>
</body>
</html>