<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/spring-social/social/tags" prefix="social" %>

<h4><a href="<c:url value="/connect"/>">Connections</a></h4>

<h4><a href="<c:url value="/twitter"/>">Twitter</a></h4>
<social:connected provider="twitter">
<ul class="menu">
	<li><a href="<c:url value="/twitter"/>">User Profile</a></li>
	<li><a href="<c:url value="/twitter/timeline"/>">Timeline</a></li>
	<li><a href="<c:url value="/twitter/friends"/>">Friends</a></li>
	<li><a href="<c:url value="/twitter/followers"/>">Followers</a></li>
	<li><a href="<c:url value="/twitter/messages"/>">Messages</a></li>
	<li><a href="<c:url value="/twitter/trends"/>">Trends</a></li>
</ul>
</social:connected>

<h4><a href="<c:url value="/facebook"/>">Facebook</a></h4>
<social:connected provider="facebook">
<ul class="menu">
	<li><a href="<c:url value="/facebook"/>">User Profile</a></li>
	<li><a href="<c:url value="/facebook/feed"/>">Feed</a></li>
	<li><a href="<c:url value="/facebook/friends"/>">Friends</a></li>
	<li><a href="<c:url value="/facebook/albums"/>">Albums</a></li>
</ul>
</social:connected>

<h4><a href="<c:url value="/linkedin"/>">LinkedIn</a></h4>
<social:connected provider="linkedin">
<ul class="menu">
	<li><a href="<c:url value="/linkedin"/>">User Profile</a></li>
	<li><a href="<c:url value="/linkedin/connections"/>">Connections</a></li>
</ul>
</social:connected>

<h4><a href="<c:url value="/vkontakte"/>">VKontakte</a></h4>
<social:connected provider="vkontakte">
    <ul class="menu">
        <li><a href="<c:url value="/vkontakte"/>">User Profile</a></li>
        <li><a href="<c:url value="/vkontakte/friends"/>">Friends</a></li>
        <li><a href="<c:url value="/vkontakte/photos/add"/>">Photos add</a></li>
        <li><a href="<c:url value="/vkontakte/photos/delete"/>">Photos delete</a></li>
    </ul>
</social:connected>