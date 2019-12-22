<%for(item in items){%>
<option value="${item.value}" <%if(item.value==value){%>selected<%}%> >${item.label}</option>
<%}%>