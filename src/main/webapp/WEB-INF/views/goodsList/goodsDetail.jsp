<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${ goods.goodsName }</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/custom.css">
</head>
<body>

<c:import url="../pcMain.jsp"></c:import>


<div  class="container-sm container-fluid d-flex flex-column align-items-center pl-0 pr-0 position-relative"  style="max-width: 520px;max-height: 100vh; overflow: auto;" >

<c:import url="../header.jsp"></c:import>

<div
    class="container-sm container-fluid d-flex flex-column justify-content-center align-items-center position-relative mb-5" id="mainContainer"
    style="max-width: 520px; margin-top: 60px">
    
    <!--상품 상세보기 이미지-->
    <c:if test="${ goodsImgs.size() != 0 }">

    <div id="carouselImg" class="carousel slide mx-0 px-0 w-100" data-ride="carousel" style="">
        <ol class="carousel-indicators">
        <li data-target="#carouselImg" data-slide-to="0" class="active"></li>
        <li data-target="#carouselImg" data-slide-to="1"></li>
        <li data-target="#carouselImg" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="${ goodsImgs[0].goodsImg }" class="d-block w-100" alt="...">
        </div>
        <c:if test="${ goodsImgs.size() >= 2 }">
        <div class="carousel-item">
            <img src="${ goodsImgs[1].goodsImg }" class="d-block w-100" alt="...">
        </div>
        </c:if>
        <c:if test="${ goodsImgs.size() == 3 }">
        <div class="carousel-item">
            <img src="${ goodsImgs[2].goodsImg }" class="d-block w-100" alt="...">
        </div>
        </c:if>
        </div>
    </div>
    </c:if>
    <c:if test="${ goodsImgs.size() == 0 }">
    <div class=" mx-0 px-0">
      <img src="${goods.goodsThumb}" alt="" class="img-fluid" width="360px" height="240px">
    </div>
    </c:if>
    <!--상품 상세보기 타이틀-->
    <div class="my-3 font-weight-bold text-center" style="font-size: 18px;">
    ${goods.goodsName}
    <input type="hidden" name="goods_idx" value="${ goods.goodsIdx }">
    <input type="hidden" name="user_idx" value="${ userIdx }">
      <!--부모클래스bg-primary 안넣었음 -->
    </div>
    <!--상품 상세보기 옵션 드롭다운-->
    <div class="col-12 d-flex flex-row justify-content-between w-75 mx-4 font-primary" style="flex: none;">
      <div class="clo-6 py-2"> 
        상품 옵션
      </div>
      <div class="col-6 dropdown border ">
        <button class="col-12 btn dropdown-toggle font-primary" type="button" id="dropdownMenuButton" data-toggle="dropdown"
          aria-expanded="false">
          옵션
        </button>
        
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
          <c:forEach var="option" items="${ goodsOptions }"> 
          <button class="dropdown-item font-primary" id="${ option.optionIdx }" type="button" onclick="totalPrice(event);">${ option.optionName }+${ option.optionPrice }</button>
          <input type="hidden" name="${ option.optionIdx }" value="${ option.optionPrice }">
          <input type="hidden" name="${ option.optionIdx }" value="${ option.optionIdx }">
 		  </c:forEach>     
        </div>
        <input type="hidden" name="option_idx" value="0">
        
      </div>
    </div>
    <!--판매가격-->
    <div class="mx-2 my-2 d-flex flex-row justify-content-between w-75 font-primary">
      <div class="col-3 py-2 font-primary">
        판매가 
      </div>
      <input type="hidden" name="goods_price" value="${ goods.goodsPrice }">
      <div class="col-4 font-primary">
      <input type="text" class="form-control-plaintext" name="goods_total_price" value="">
      <%-- <fmt:formatNumber value="${goods.goods_price}" type="number" /> --%>
      </div>
    </div>
    <div class="w-100 bg-secondary" style="height: 20px">
      <!--bg-dark-50-->
    </div>
    <!--상품정보 리뷰 문의/안내 네브-->
    <nav class="w-100">
      <div
        class="navMenu d-flex flex-row justify-content-around mb-2 mt-1 font-weight-bold border-bottom w-100 text-center"
        style="height: 45px;">
        <div class="col-4 info on pt-2" onclick="setMenu('info')">상품 정보</div>
        <div class="col-4 review pt-2" onclick="setMenu('review')">리뷰</div>
        <div class="col-4 inquiry pt-2" onclick="setMenu('inquiry')">문의/안내</div>
      </div>
    </nav>


    <!--해당 페이지 네브 선택된 화면만 보여지기-->
    <main class="info w-100">
      <div class="dep _info">
        <div class="d-block text-center mx-5 my-4">
          <img src="${goods.goodsDetail}" alt="" class="img-fluid">
         
           <!--구매하기버튼/장바구니 아이콘-->
          
          <div class="d-flex w-100 my-2 py-2 pl-4 border border-dark-50 overflow">
            <!--장바구니 버튼을 누르면 장바구니에 해당상품 페이지에 보고있던 상품이 추가된다-->
            <img src="/img/icon/bag.png" id="justAdd"
              onclick="addCart(event);" style="width: 40px; height: 40px; cursor: pointer;">
            <!--구매하기 버튼을 누르면 장바구니페이지로 이동하고 장바구니에 해당상품 페이지에 보고있던 상품이 추가된다-->
            <button type="submit" class="btn btn-primary col-8 ml-4" id="addAndBuy" onclick="addCart(event);">구매하기</button>
            </div>
         
        </div>
      </div>
      
      <!--해당네브:리뷰-->
      <div class="dep _review ">
        <div class="d-flex flex-column justify-content-start my-3">
       
       
        <c:forEach var = "dto" items="${reviewList}">
          
          <div class="card mb-3 col-10 pl-0" style="max-width: 520px;">
			  <div class="row no-gutters h-50 position-relative">
			  <!-- 등록된 리뷰이미지가 있으면 표시하고, 없으면 대체이미지 표시함 -->
			  <c:set var="isExistImg" value="0" />
			  <c:forEach var="review_img" items="${ reviewImgList }">
	        		<c:if test="${ reviewImgList.size() != 0 && review_img.getReviewEntity().getReviewIdx() eq dto.reviewIdx}">
	        			<c:set var="img" value="${ review_img.reviewImg }" />
	        			<c:set var="isExistImg" value="1" />
	        			<div class="col-5">
		          			<div class="w-100" style=" padding-bottom: 75%; height: 0;">
		          				<img src="${ img }" class="" alt="${ goods.goodsIdx }"
		          				 style="position: absolute; left: 0; top: 0; width: 100%; height: 100%;">
		          			</div>
	          			</div>
	          			<c:remove var="img"/>
	          		</c:if>	
	          	</c:forEach>
	          		<c:if test="${ isExistImg == 0 }">
	          			<div class="col-5">
		          			<div class="w-100" style="padding-bottom: 75%; height: 0;">
		          				<img src="/img/img_not_found.png" class="" alt="등록된 이미지가 없습니다."
		          				 style="position: absolute; left: 0; top: 0; width: 100%; height: 100%;">
		          			</div>
	          			</div>
	          		</c:if>
	          		<c:remove var="isExistImg"/>
	          
			    <div class="col-7 float-right">
			      <div class="card-body col-12 d-flex flex-column align-items-start justify-content-center px-1 py-0">
			        <p class="card-title"><small>★ ${ dto.reviewStar }</small></p>
			        <p class="card-text my-2">${ dto.reviewContents }</p>
			        <p class="card-text my-1 align-self-end"><small class="text-muted">${ dto.reviewDate }</small></p>
			        <!-- 등록된 답글이 있으면 답글보기 버튼이 노출됨 -->
			        <c:if test="${ dto.reviewIsReplied == 1 }">
			        	<p class="card-text align-self-end" onclick="popupHideAndShow('reply${ dto.reviewIdx}')">
			        		<small class="text-muted" style="cursor: pointer;">답글보기</small>
		        		</p>
			        </c:if>
			      </div>
			    </div>
			  </div>
			  <!-- 답글팝업 -->
			<div class="card col-11 position-absolute d-none justify-contents-center" id="reply${ dto.reviewIdx }"
			 style="top: 96%; right: 4%; z-index: 1500;">
			  <div class="card-body">
			    <p class="card-text my-1">${ dto.reviewReply }</p>
			    <p class="card-text my-1"><small class="text-muted">${ dto.reviewReplyDate }</small></p>
			    <button class="btn btn-primary float-right mb-2" type="button" 
			     onclick="popupHideAndShow('reply${ dto.reviewIdx}')">닫기</button>
			  </div>
			</div>
		</div>
			
          
          </c:forEach>
          
        </div>
      </div>
      <!--해당네브:문의/안내-->
      
      <div class="dep _inquiry">
        <div class="py-3 mb-4">
        
        <!--아코디언-->
        
          <div class="accordion" id="accordion">
         
          <c:forEach var="question" items="${ questionList }">
            <div class="card">
            	<!-- 질문 제목 -->
              <div class="card-header p-0" id="heading${ question.questionIdx }">
                <h2 class="mb-0">
                  <button class="btn btn-block text-left d-flex flex-row justify-content-between font-primary" type="button"
                    data-toggle="collapse" data-target="#collapse${ question.questionIdx }" aria-expanded="true" aria-controls="collapse${ question.questionIdx }"
                    style="height: 40px">
                    <span>${ question.questionTitle }</span><img src="/img/icon/down.png" alt="" class="img-fluid"
                      style="width: 25px; height: 25px;">
                  </button>
                </h2>
              </div>
              <!--아코디언 내용-->
              <div id="collapse${ question.questionIdx }" class="collapse font-primary" aria-labelledby="heading${ question.questionIdx }" data-parent="#accordion">
                <div class="card-body">
                  <p class="card-text my-1">${ question.questionContents }</p>
                  <p class="card-text my-1 float-right"><small class="text-muted">${ question.questionDate }</small></p>
                </div>
                <!-- 답변이 달려있으면 답변도 같이 노출 -->
                <c:if test="${ question.questionIsReplied == 1 }">
                	<div class="card-body pt-1 border-top">
                		<h6 class="card-title mt-1 mb-2">답변 드립니다.</h6>
                		<p class="cart-text my-1">${ question.questionReply }</p>
                		 <p class="card-text my-1 float-right"><small class="text-muted">${ question.questionReplyDate }</small></p>
                	</div>
                </c:if>
              </div>
            </div>
            
            </c:forEach>
            
          </div>
          <!--상품 문의하기 버튼-->
          <div class="container w-100 my-5 d-flex justify-content-center">
            <button type="button" class="btn btn-primary" onclick="popupHideAndShow('goodDetailInquiryPop')">
              상품 문의하기
            </button>
          </div> 
        </div>
      </div>
    </main>
    
    <!--상품문의 작성팝업-->
    <div class="d-none position-absolute" id="goodDetailInquiryPop" style="bottom: 30px">
      <div class="d-flex flex-column border rounded bg-white mx-auto">
        <!--X아이콘-->
        <div class="d-flex justify-content-end">
          <img src="/img/icon/cross.png" alt=""
            onclick="popupHideAndShow('goodDetailInquiryPop')" style="width:30px; height: 30px; cursor: pointer;">
        </div>
        <div class="mr-3 mb-4 ml-3">
          <div class="text-center font-weight-bold text-dark mb-2">
            상품문의
          </div>
          <form action="productQnaWriteAction" method="post" name="productQnaWriteForm">
            <div class="d-block mb-1">
              <input type="text" placeholder="상품 문의 제목을 입력해주세요." class="border rounded w-100 text-dark py-1 px-3 font-primary" name="question_title">
            </div>
            <div class="d-block">
              <textarea cols="50" rows="10" placeholder="상품문의 내용을 입력해주세요." class="border rounded w-100 text-dark py-1 px-3 mb-4 font-primary"
                name="question_contents"
                style="resize: none;"></textarea>
            </div>
            <!--자동으로 받음-->
            <input type="hidden" name="user_idx" value="${ userIdx }">
            <input type="hidden" name="goods_idx" value="${ goods.goodsIdx }">
            
            <button type="button" class="btn btn-secondary text-dark col-3" onclick="popupHideAndShow('goodDetailInquiryPop')">취소 </button>
            <input type="submit" class="btn btn-dark text-light ml-2 col-6" onsubmit="return checkLogin();" value="문의하기">
          </form>
        </div>
      </div>
    </div>
  </div>

    
<c:import url="../footer.jsp"></c:import>
<c:import url="../nav.jsp"></c:import>
</div>

<!-- bootstrap js  // jquery js는 nav에 들어있는채로 import-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF" crossorigin="anonymous"></script>
<script src="/js/main.js"></script>
    <script>
      function setMenu(_type) {
        let types = document.querySelectorAll("div .navMenu div");
        let container = document.querySelector("main");
        types.forEach(function (type) {
          type.classList.remove('on');
        });
        document.querySelector("div .navMenu div." + _type).classList.add("on");
        container.classList.remove('info');
        container.classList.remove('review');
        container.classList.remove('inquiry');
        container.classList.add(_type);
      };
      
      let total_Price = document.getElementsByName('goods_total_price')[0];
      total_Price.value = document.getElementsByName('goods_price')[0].value;
    </script>
</body>
</html>