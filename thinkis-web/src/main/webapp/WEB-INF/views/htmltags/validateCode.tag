<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js"></script>
<input class="layui-input" id="${name}" name="${name}" maxlength="5" placeholder="验证码" lay-verify="required|validatecode" type="text" autocomplete="off" style="width:120px;${inputCssStyle!}">
<img src="${ctxPath}/servlet/validateCodeServlet" onclick="$('.${name}Refresh').click();" class="mid ${name}" style="${imageCssStyle!}"/>
<a href="javascript:" onclick="$('.${name}').attr('src','${ctxPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid ${name}Refresh" style="${buttonCssStyle!}">看不清</a>
