<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body>		
	<div class="block-half block-left">
		<img src="resources/images/Logo2.png">
	</div>

	<div class="block-half block-right">
		<h1><spring:message code="lbl.welcome"/></h1>
		<p><br><spring:message code="lbl.subscribed"/></p>
		<p><br>
		<button class="btn"><spring:message code="lbl.button-start"/></button>
	</div>
	
	<div class="clearer"></div>
	
	<script>
		$(".btn").click(function(){
			window.location = "login.do";
		});	
	
	</script>
</body>
