<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false" %>

<h3>Your VK Profile</h3>
<p>Hello, <c:out value="${uploadserver.upload_url}"/>!</p>
<dl>
	<dt>VKontakte ID:</dt>
	<dd><c:out value="${uploadserver.aid}"/></dd>
	<dt>Name:</dt>
	<dd><c:out value="${uploadserver.mid}"/></dd>
</dl>