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
</s:if>
<s:if test="hasActionMessages()">
	<div class="boxMessage">
		<button class="close" type="button">×</button>
		<s:actionmessage />
	</div>
</s:if>