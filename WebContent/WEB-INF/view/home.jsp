<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<title>바코드 띄우기</title>
</head>
<script type="text/javascript">



	$(function(){
		
		
	     //input을 datepicker로 선언
        $("#expire").datepicker({
            dateFormat: 'ymmdd' //Input Display Format 변경
            ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
            ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
            ,changeYear: true //콤보박스에서 년 선택 가능
            ,changeMonth: true //콤보박스에서 월 선택 가능                
            ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
            ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
            ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
            ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
            ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
            ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
            ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
            ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
            ,minDate: "0" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
            ,maxDate: "+1Y" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)                
            
        });                    
        
        //초기값을 오늘 날짜로 설정
        $('#expire').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)     
        
		/*모달창 켰을때 포커스처리  */
        $("#myModal").on('shown.bs.modal', function(){
            $(this).find('#searchBarcode').focus();
        });
		
		
		//바코드 조회버튼
		$('#goBTN').click(function(){
			
			var inputBarcode = $('#searchBarcode').val();
			if(inputBarcode.length<=0){
				alert("스캔을 해주세요!");
				return false;
			}
			$(location).attr('href','${pageContext.request.contextPath}/search?inputBarcode='+inputBarcode);
				//alert(inputBarcode);
			
		})
		
		var gtincode = $('#gtinSpan').val();
	
		if(gtincode.length>0){
			$('select[name=gs1] option[value='+gtincode+']').attr('selected',true);
			//alert(gtincode);
			
		}
		
		
		//GTIN코드 보여주기..		
		$('#gs1').change(function(){
			var data = $(this).val();
			$('#gtinSpan').append().html(data);
		})
		
		
		/* 정규식과 바코드 생성하는 버튼 클릭 */
		$("#gen").click(function(){
		
					//상품식별코드 발리데이션(01)
					var gs1 = /^[0-9]{13,14}$/;
					var gs1code = $('select[name=gs1]').val();
					if(!gs1.test(gs1code) || gs1code.length<=0)   {  
				    	alert("상품식별코드가 선택되지 않았습니다.") 
				    	return false; 
				    }
					
					//유효기간입력 발리데이션(17)
				    var exprieNum = /^\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$/;
				    var expireIn = $("#expire").val().trim();
				    if(!exprieNum.test(expireIn) || expireIn.length<=0)   {  
				    	alert("유효기간 형식이 잘못되었습니다.") 
				    	return false; 
				    }
				    
				    //제품제조번호(10)
				    var lineNum = /^[!%&)(*+,-./_:;/>/</=/?a-zA-Z0-9]{5}$/;
				    var lineNumIn =$('#lNum').val().trim();
				    if(!lineNum.test(lineNumIn) || lineNumIn.length<=0)   {  
				    	alert("제품제조번호 코드가 잘못되었습니다.") 
				    	return false; 
				    }
				    
				    //제품일련번호(21)
				    var serialNum = /^[!%&)(*+,-./_:;/>/</=/?a-zA-Z0-9]{1,20}$/;
				    var serialNumIn = $('#sNum').val().trim();
				    if(!serialNum.test(serialNumIn) || serialNumIn.length<=0)   {  
				    	alert("제품일련번호가 잘못되었습니다.") 
				    	return false; 
				    }
				    
				    //바코드생성하는 메서드로 ㄱㄱ
		
			   var fullBarcode = "01"+gs1code+"17"+expireIn+"10"+lineNumIn+"21"+serialNumIn;
			//	   alert("유효기간입력 : "+expireIn+"제품제조번호  : "+lineNumIn + "제품일련번호 : " + serialNumIn);
						
					 	 $.ajax({
							
							type : 'post'
							,url : '${pageContext.request.contextPath}/genBarcode'
							,data :{fullBarcode :fullBarcode }
							,error : function(result){
									alert(result.status);
							}
							,success : function(result){
								
									var code = "";
									code += "<img alt='바코드' src='/img/barcode.gif' width='100' height='100'>";
									code += "<p>전체 바코드 : (01)"+result.substring(2,16)+"(17)"+result.substring(18,24)+"(10)"+result.substring(26,31)+"(21)"+result.substring(33)+" </p>"
									$('#box').append().html(code);
							}
						}) 
						alert("바코드가 생성되었습니다.");     
		})
		    
		//라벨로 인쇄하기 위한 버튼 클릭	
	 $('#goLabel').click(function(){
		    	
		    	//상품식별코드 발리데이션(01)
				var gs1 = /^[0-9]{13,14}$/;
				var gs1code = $('select[name=gs1]').val();
				if(!gs1.test(gs1code) || gs1code.length<=0)   {  
			    	alert("상품식별코드가 선택되지 않았습니다.") 
			    	return false; 
			    }
				
				
				//유효기간입력 발리데이션(17)
			    var exprieNum = /^\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$/;
			    var expireIn = $("#expire").val().trim();
			    if(!exprieNum.test(expireIn) || expireIn.length<=0)   {  
			    	alert("유효기간 형식이 잘못되었습니다.") 
			    	return false; 
			    }
			    
			    //제품제조번호(10)
			    var lineNum = /^[!%&)(*+,-./_:;/>/</=/?a-zA-Z0-9]{5}$/;
			    var lineNumIn =$('#lNum').val().trim();
			    if(!lineNum.test(lineNumIn) || lineNumIn.length<=0)   {  
			    	alert("제품제조번호 코드가 잘못되었습니다.") 
			    	return false; 
			    }
			    
			    //제품일련번호(21)
			    var serialNum = /^[!%&)(*+,-./_:;/>/</=/?a-zA-Z0-9]{1,20}$/;
			    var serialNumIn = $('#sNum').val().trim();
			    if(!serialNum.test(serialNumIn) || serialNumIn.length<=0)   {  
			    	alert("제품일련번호가 잘못되었습니다.") 
			    	return false; 
			    }
			    
			    //바코드라벨프린터
 		  $.ajax({
					
					type : 'post'
					,url : '${pageContext.request.contextPath}/print'
					,data :{ "gs1code": gs1code, "expireIn": expireIn,"lineNumIn":lineNumIn,"serialNumIn":serialNumIn  }
					,error : function(result){
							alert(result.status);
					}
					,success : function(result){
							alert("인쇄성공");
					}
				}) 
				alert("바코드가 생성되었습니다.");     
		    });
		    
		    
		});
		
</script>
<style>
h2, #putdong, h4 {	font-family: 'Black Han Sans', sans-serif;}
label {	display: inline-block;	width: 200px;	margin: 10px;}
input {	margin: 10px;}
p.dotted {	border-bottom-style: dotted;	border-bottom-color: #F2F2F2;	margin: 10px;	padding: 10px;}
#box {	width: 100%;	height: 200px;	border: 1px solid black;	padding: 10px;}
#divv,#diva{   box-shadow: -60px 0px 100px -90px #000000,60px 0px 100px -90px #000000;}
#divv2{ box-shadow: -60px 0px 100px -90px #000000,60px 0px 100px -90px #000000;}

/*datepicer 버튼 롤오버 시 손가락 모양 표시*/
.ui-datepicker-trigger{cursor: pointer;}

/*datepicer input 롤오버 시 손가락 모양 표시*/
.hasDatepicker{cursor: pointer;}        

/*모달버튼  */
#goBTN{position: relative;    position: rea;    left: 1.2vw;}        
</style>
<body>


	<div class="container">
	<div class="container" id="diva">
		<h2>GS1-128 바코드 생성_(주)한국얀센</h2>
		<br>
		<br>
		<form class="form-inline" id="generateForm" method="post">
			<div class="form-group">
				<p class="dotted" id="genBarcodeP">
					<label for="id">상품식별코드(01)</label>
					 <select class="form-control"id="gs1" name="gs1">
						<option>GS1상품코드</option>
						<option value="08806469007114">타이레놀정160밀리그람(아세트아미노펜)</option>
						<option value="08806469005639">어린이타이레놀현탁액(60밀리리터)</option>
					</select>
						<c:if test="${!empty zz }">
							<input id="gtinSpan" class="form-control"  type="text" readonly="readonly"value="${fn:substring(zz.gs1code,2,18) }">
						</c:if>
						
						<c:if test="${empty zz }">
							<span id="gtinSpan" class="form-control"  ></span>
						</c:if>
					
				</p>


				<p class="dotted" id="genBarcodeP">
					<label for="id">유효기간(17)</label> 
					<c:if test="${!empty zz }">
						<input class="form-control"	id="" name="expire" placeholder="YYMMDD" readonly="readonly" value="${fn:substring(zz.expire,2,8) }">
					</c:if>
					<c:if test="${empty zz }">
						<input class="form-control"	id="expire" name="expire" placeholder="YYMMDD" readonly="readonly" >
					</c:if>
				</p>

				<p class="dotted" id="genBarcodeP">
					<label for="id">제품제조번호(10)</label>
					<c:if test="${!empty zz }">
						 <input class="form-control"id="lNum" name="lNum" placeholder="5자리입력 "value="${zz.lNum }">
					 </c:if>
					 <c:if test="${empty zz }">
						 <input class="form-control"id="lNum" name="lNum" placeholder="5자리입력">
					 </c:if>
				</p>
				<p class="dotted" id="genBarcodeP">
					<label for="id">제품일련번호(21)</label> 
					<c:if test="${!empty zz }">
						<input class="form-control"	id="sNum" name="sNum" placeholder="20자리이하입력" value="${zz.sNum }">
					</c:if>	
					 <c:if test="${empty zz }">
					 	<input class="form-control"	id="sNum" name="sNum" placeholder="20자리이하입력" >
					</c:if>	
					
				</p>
			<c:if test="${empty zz }">	
				<p class="dotted" id="genBarcodeP">
					<label for="id">전체바코드 코드확인 및 생성</label>
					<button id="gen" type="button" class="btn btn-success">생성하기</button>
				<button id="goLabel" type="button" class="btn btn-warning">라벨로 인쇄하기</button>
				</p>
			</c:if>
			<c:if test="${!empty zz }">
				<p class="dotted" id="genBarcodeP">
					<label for="id">처음화면으로</label>
					<a href="${pageContext.request.contextPath }/home" style="color: red;">처음화면으로</a>
				</p>
			</c:if>
			</div>
		</form>



		<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ  -->

<c:if test="${empty zz }">	
		<div id="box" class="form-group">
<!-- 						<img alt="바코드" src="/img/barcode.gif" >  -->
		</div>
		<p class="dotted">
			<i style="font-size: 24px" class="fa">&#xf0de;</i><a href="#"
				onclick="printArea()" id="gogoprint">생성된 바코드 인쇄하려면 여기를 누르세요</a>
		</p>
</c:if>		
		</div>
		
<script type="text/javascript">
		var inBody;
		function beforePrint(){
			boxes = document.body.innerHTML;
			document.body.innerHTML = box.innerHTML;
		}
		
		function afterPrint(){
			document.body.innerHTML = boxes;
		}
		function printArea(){
			window.print();
		}
		
		window.onbeforeprint = beforePrint;
		window.onafterprint = afterPrint;
</script>
<br>
		<c:if test="${empty zz }">	
			<div class="container" id="divv2">
				<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal" id="bbb">바코드 스캔</button>
			</div>
		</c:if>

		  <!-- Modal -->
		  <div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog">
		    
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		          <h4 class="modal-title">스캐너로 바코드 조회</h4>
		        </div>
		        <div class="modal-body">
		       
					<label >스캔해주세요</label> <br>
					<input type="text"	class="form-control" id="searchBarcode" name="searchBarcode"  >
					<button type="button" class="btn btn" id="goBTN">조회</button> 
						
		        </div>
		        <div class="modal-footer">
		          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        </div>
		      </div>
		      
		    </div>
		  </div>
<br>
</div>
	
</body>
</html>