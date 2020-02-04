<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>바코드 띄우기22222222222</title>
</head>
<script type="text/javascript">

	$(function(){
		
		
		
		
		//바코드 조회버튼
		$('#goBTN').click(function(){
			
			var searchBarcode = $('#searchBarcode').val();
				alert(	searchBarcode.substring(0,4));
			
		})
		
		
		
		
		//GTIN코드 보여주기..		
		$('#gs1').change(function(){
			var data = $(this).val();
			$('#gtinSpan').append().html(data);
		})
		
		
		
		/* 정규식 */
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
		    var lineNum = /^[!%&)(*+,-./_:;/>/</=/?a-zA-Z0-9]{1,20}$/;
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
//		    byte gs = 0x1D;
//		    byte gf = 0xE8;
	   var fullBarcode = "01"+gs1code+"17"+expireIn+"10"+lineNumIn+"21"+serialNumIn;
//		   var fullBarcode = "(01)"+gs1code+"(17)"+expireIn+"(10)"+lineNumIn+"(21)"+serialNumIn;
		    
			$('#generateForm').attr('action','${pageContext.request.contextPath}/genBarcode?fullBarcode='+fullBarcode);
			
		 //  alert("유효기간입력 : "+expireIn+"생산라인코드  : "+lineNumIn + "제품일련번호 : " + serialNumIn);
		    alert(fullBarcode);
		    
		    
		    
		    
		    
		//		alert("바코드가 생성되었습니다.");    
				
				
			 	 $.ajax({
					
					type : 'post'
					,url : '${pageContext.request.contextPath}/genBarcode'
					,data :{fullBarcode :fullBarcode }
					,error : function(result){
						
							alert(result.status);
					}
					,success : function(result){
						
						//	alert(result);
							
							var code = "";
							code += "<img alt='바코드' src='/img/barcode.gif'>";
							code += "<p>출력된 바코드 :"+result+" </p>"
							
							$('#box').append().html(code);
					}
					
				}) 
				 
				
				alert("바코드가 생성되었습니다.");     
			
		})
		    
		    
		    
		});
</script>
<style>
#spanjoin {
	display: none;
}

h2, #putdong, h4 {
	font-family: 'Black Han Sans', sans-serif;
}

label {
	display: inline-block;
	width: 200px;
	margin: 10px;
}

input {
	margin: 10px;
}

p.dotted {
	border-bottom-style: dotted;
	border-bottom-color: #F2F2F2;
	margin: 10px;
	padding: 10px;
}

#box {
	width: 100%;
	height: 200px;
	border: 1px solid black;
	padding: 10px;
}
#divv,#diva{   box-shadow: -60px 0px 100px -90px #000000,
        60px 0px 100px -90px #000000;}
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
					<span id="gtinSpan" style="color: red; float: right;"></span>
				</p>



				<p class="dotted" id="genBarcodeP">
					<label for="id">유효기간입력(17)</label>
					 <input class="form-control" id="expire" name="expire" placeholder="YYMMDD">
				</p>

				<p class="dotted" id="genBarcodeP">
					<label for="id">제품제조번호(10)</label>
					 <input class="form-control" id="lNum" name="lNum" placeholder="20자리이하입력">
				</p>
				<p class="dotted" id="genBarcodeP">
					<label for="id">제품일련번호(21)</label> 
					<input class="form-control"		id="sNum" name="sNum" placeholder="20자리이하입력">
				</p>
				<p class="dotted" id="genBarcodeP">
					<label for="id">전체바코드 코드확인 및 생성</label>
					<button id="gen" type="button" class="btn btn-success">생성하기</button>
				</p>
			</div>
		</form>



		<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ  -->


		<div id="box" class="form-group">
			<!-- 			<img alt="바코드" src="/img/barcode.gif">  -->
		</div>
		<p class="dotted">
			<i style="font-size: 24px" class="fa">&#xf0de;</i><a href="#"
				onclick="printArea()" id="gogoprint">생성된 바코드 인쇄하려면 여기를 누르세요</a>
		</p>
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

<br><br><br>
	<div class="container" id="divv">
		<form class="form-inline" id="searchForm">
			<div class="form-group">
				<p class="dotted">
					<label for="name">스캐너 바코드 조회하기</label> <input type="text"
						class="form-control" id="searchBarcode" name="searchBarcode">
					<button type="button" class="btn btn" id="goBTN">조회</button>
				</p>
			</div>
		</form>
		</div>
	</div>
	
</body>
</html>