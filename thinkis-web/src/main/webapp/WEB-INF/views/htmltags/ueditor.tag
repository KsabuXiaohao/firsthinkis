<script type="text/javascript">include('ueditor_lib','${ctxStatic}/ueditor1.4.3/',['ueditor.config.js','ueditor.all.js','lang/zh-cn/zh-cn.js']);</script>
<textarea id="${id}" name="${name}" value="${content}" lay-verify="${has(required)?'required':''}"></textarea>
<script type="text/javascript">
    $(function(){

        //富文本编辑器
        var ueditor = UE.getEditor('${id}',{
            initialFrameHeight: ${height!}
            <%if(has(width)){%>
            ,initialFrameWidth : ${width}
            <%}%>
            <%if(!has(allToolbar) || (has(allToolbar)&&allToolbar=="false")){%>
            ,toolbars: [
                ['fullscreen', 'source', 'undo', 'redo', 'bold', 'italic', 'underline', 'fontborder', 'backcolor', 'fontsize', 'fontfamily', 'justifyleft', 'justifyright', 'justifycenter', 'justifyjustify', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', 'link', 'unlink', 'emotion', 'insertimage']
            ]
            <%}%>
        });
        ueditor.ready(function() {
            ueditor.setContent('${@Encodes.unescapeHtml(content)}');
        });

        UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
        UE.Editor.prototype.getActionUrl = function(action) {
            if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadfile' ) {
                var type = "files";
                if(action == 'uploadimage' || action == 'uploadscrawl'){
                    type = "images";
                }
                return ctx+'/file/uploadFile?type='+type;
            } else if (action == 'uploadvideo') {
                return ctx+'/file/uploadFile?type=videos';
            } else {
                return this._bkGetActionUrl.call(this, action);
            }

        }
    });
</script>