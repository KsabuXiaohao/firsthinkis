<%for(item in items){%>
<input type="radio" name="${name}" value="${item.value}" title="${item.label}" <%if(item.value==value){%>checked<%}%>/>
<%}%>