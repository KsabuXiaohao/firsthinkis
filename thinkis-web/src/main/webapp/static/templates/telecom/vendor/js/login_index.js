$(function(){
    $("#login").click(function(){
        var username = $("#username").val();
        var password = $("password").val();
        $.ajax({
        url: " ",
        type: "POST",
        dataType: "json",
        data: {username: username, password: password},
        success: function(flag){
            if(flag == "0"){
                alert("用户名或密码错误！")
            }
        },
        error: function(){
            alert("error!");
        }
        });
    })
})
