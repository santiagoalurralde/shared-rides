<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h1>
Perfil de contactos
</h1>

			<h1>${id}</h1>
			<h1>${name}</h1>
			<h1>${surname}</h1>
			<h1>${address}</h1>
			<h1>${neighborhood}</h1>
			<h1>${telephone}</h1>
			<h1>${email}</h1>
			<h1>${shift}</h1>
			<h1>${idPedestrian}</h1>
			<h1>${ratingPedestrian}</h1>
			
			
			<c:forEach var="sch" items="${schPed}">
				<c:forEach var="day" items="${sch}">
					<c:out value="${day}"/>
				</c:forEach>
			</c:forEach>


			<h1>${lat}</h1>
			