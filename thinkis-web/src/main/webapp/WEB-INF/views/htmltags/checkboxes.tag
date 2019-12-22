<%for(item in items){%>
    <input type="checkbox" name="${name}" title="${item.label}" value="${item.value}" <%if(array.contain(value,item.value)){%>checked<%}%>>
<%}%>