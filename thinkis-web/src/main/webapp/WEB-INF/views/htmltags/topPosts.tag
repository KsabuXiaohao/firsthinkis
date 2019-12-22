<div class="fly-panel">
    <div class="fly-panel-title fly-filter">
        <a>置顶</a>
    </div>
    <ul class="fly-list">
        <%
            var posts = @CmsUtils.getArticleList(siteId,categoryId,4,"posid:1, orderBy: 'hits desc'");
            for(var post in posts){
        %>
        <li>
            <a href="user/home.html" class="fly-avatar">
                <img src="${post.user.photo}" alt="${post.user.name}">
            </a>
            <h2>
                <a class="layui-badge">${post.category.name}</a>
                <a href="${post.url}">${post.title}</a>
            </h2>
            <div class="fly-list-info">
                <a href="user/home.html" link>
                    <cite>${post.user.name}</cite>
                </a>
                <span>${post.updateDate,dateFormat="yyyy-MM-dd HH:mm:ss "}</span>

                <span class="fly-list-nums">
                    <i class="iconfont" title="人气">&#xe60b;</i>${post.hits}
                    <i class="iconfont icon-pinglun1" title="评论"></i>${post.commentCount}
                </span>
            </div>
            <div class="fly-list-badge">
                <span class="layui-badge layui-bg-red">精帖</span>
            </div>
        </li>
        <%}%>
    </ul>
</div>