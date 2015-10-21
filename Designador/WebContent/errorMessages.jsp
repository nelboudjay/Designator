<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="hasActionErrors()">
	<div class="errors">
		<button class="close" type="button">×</button>
		<s:actionerror />
	</div>

</s:if>

<s:if test="hasFieldErrors()">
	<div class="errors">
		<button class="close" type="button">×</button>
		<s:fielderror />
	</div>  
	
	<s:set var="keys" value="''"/>
	
	<s:iterator  value="fieldErrors" >
		<s:set var="keys" value="#keys + ',#'  + key "/>
	</s:iterator>
		
	<script type="text/javascript">
		var key = 	"<s:property value='#keys.substring(1)' />";
		
		 $(document).ready(function() {
			 $("" + key).css({"border-color" : "#b94a48", "border-style" : "solid"});
		 });
	 </script> 
    
</s:if>

<s:if test="hasActionMessages()">
	<div class="boxMessage">
		<button class="close" type="button">×</button>
		<s:actionmessage />
	</div>
</s:if>