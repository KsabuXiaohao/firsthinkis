/**

 layui官网

*/

layui.define(['code', 'element', 'table', 'util'], function(exports){
  var $ = layui.jquery
  ,element = layui.element
  ,layer = layui.layer
  ,form = layui.form
  ,util = layui.util
  ,device = layui.device();


  //阻止IE7以下访问
  if(device.ie && device.ie < 8){
    layer.alert('最低支持ie8，您当前使用的是古老的 IE'+ device.ie + '，你丫的肯定不是程序猿！');
  }

  //切换版本
  form.on('select(tabVersion)', function(data){
    var value = data.value;
    location.href = value === 'new' ? '/' : ('/' + value);
    //location.href = '/?s=index/Guide';
  });
  

  //首页banner
  setTimeout(function(){
    $('.site-zfj').addClass('site-zfj-anim');
    setTimeout(function(){
      $('.site-desc').addClass('site-desc-anim')
    }, 5000)
  }, 100);


  //数字前置补零
  var digit = function(num, length, end){
    var str = '';
    num = String(num);
    length = length || 2;
    for(var i = num.length; i < length; i++){
      str += '0';
    }
    return num < Math.pow(10, length) ? str + (num|0) : num;
  };


  //下载倒计时
  var setCountdown = $('#setCountdown');
  if($('#setCountdown')[0]){
    $.get('/api/getTime', function(res){
      util.countdown(new Date(2017,7,21,8,30,0), new Date(res.time), function(date, serverTime, timer){
        var str = digit(date[1]) + ':' + digit(date[2]) + ':' + digit(date[3]);
        setCountdown.children('span').html(str);
      });
    },'jsonp');
  }



  //获取Github数据
  var getStars = $('#getStars');
  var getFork = $('#getFork');
  var getWatch = $('#getWatch');

  var getStars_fast = $('#getStars_fast');
  var getFork_fast = $('#getFork_fast');
  var getWatch_fast = $('#getWatch_fast');

  if(getStars[0]){
    $.get('https://gitee.com/api/v5/repos/renrenio/renren-security', function(res){
      getStars.html(res.stargazers_count);
      getFork.html(res.forks_count);
      getWatch.html(res.watchers_count);
    }, 'json');
  }

    if(getStars_fast[0]){
    $.get('https://gitee.com/api/v5/repos/renrenio/renren-fast', function(res){
      getStars_fast.html(res.stargazers_count);
      getFork_fast.html(res.forks_count);
      getWatch_fast.html(res.watchers_count);
    }, 'json');
  }


  //窗口scroll
  ;!function(){
    var main = $('.site-tree').parent(), scroll = function(){
      var stop = $(window).scrollTop();

      if($(window).width() <= 750) return;

      if(!$("html").hasClass("footer")){
         var bottom = $('.footer').offset().top - $(window).height();
      }else{
        var bottom = $('.footer').offset().top - $(window).height();
      }


      if(stop > 61 && stop < bottom){
        if(!main.hasClass('site-fix')){
          main.addClass('site-fix');
        }
        if(main.hasClass('site-fix-footer')){
          main.removeClass('site-fix-footer');
        }
      } else if(stop >= bottom) {
        if(!main.hasClass('site-fix-footer')){
          main.addClass('site-fix site-fix-footer');
        }
      } else {
        if(main.hasClass('site-fix')){
          main.removeClass('site-fix').removeClass('site-fix-footer');
        }
      }
      stop = null;
    };
    scroll();
    $(window).on('scroll', scroll);
  }();




  //示例页面滚动
  $('.site-demo-body').on('scroll', function(){
    var elemDate = $('.layui-laydate')
    ,elemTips = $('.layui-table-tips');
    if(elemDate[0]){
      elemDate.each(function(){
        var othis = $(this);
        if(!othis.hasClass('layui-laydate-static')){
          othis.remove();
        }
      });
      $('input').blur();
    }
    if(elemTips[0]) elemTips.remove();

    if($('.layui-layer')[0]){
      layer.closeAll('tips');
    }
  });
  
  //代码修饰
  layui.code({
    elem: 'pre',
    about: false
  });
  
  //目录
  var siteDir = $('.site-dir');
  if(siteDir[0] && $(window).width() > 750){
    layer.ready(function(){
      layer.open({
        type: 1
        ,content: siteDir
        ,skin: 'layui-layer-dir'
        ,area: 'auto'
        ,maxHeight: $(window).height() - 300
        ,title: '目录'
        //,closeBtn: false
        ,offset: 'r'
        ,shade: false
        ,success: function(layero, index){
          layer.style(index, {
            marginLeft: -15
          });
        }
      });
    });
    siteDir.find('li').on('click', function(){
      var othis = $(this);
      othis.find('a').addClass('layui-this');
      othis.siblings().find('a').removeClass('layui-this');
    });
  }

  //在textarea焦点处插入字符
  var focusInsert = function(str){
    var start = this.selectionStart
    ,end = this.selectionEnd
    ,offset = start + str.length

    this.value = this.value.substring(0, start) + str + this.value.substring(end);
    this.setSelectionRange(offset, offset);
  };

  //演示页面
  $('body').on('keydown', '#LAY_editor, .site-demo-text', function(e){
    var key = e.keyCode;
    if(key === 9 && window.getSelection){
      e.preventDefault();
      focusInsert.call(this, '  ');
    }
  });

  var editor = $('#LAY_editor')
  ,iframeElem = $('#LAY_demo')
  ,demoForm = $('#LAY_demoForm')[0]
  ,demoCodes = $('#LAY_demoCodes')[0]
  ,runCodes = function(){
    if(!iframeElem[0]) return;
    var html = editor.val();

    html = html.replace(/=/gi,"layequalsign");
    html = html.replace(/script/gi,"layscrlayipttag");
    demoCodes.value = html.length > 100*1000 ? '<h1>卧槽，你的代码过长</h1>' : html;

    demoForm.action = '/api/runHtml/';
    demoForm.submit();

  };
  $('#LAY_demo_run').on('click', runCodes), runCodes();

  //让导航在最佳位置
  var thisItem = $('.site-demo-nav').find('dd.layui-this');
  if(thisItem[0]){
    var itemTop = thisItem.offset().top
    ,winHeight = $(window).height()
    ,elemScroll = $('.layui-side-scroll');
    if(itemTop > winHeight - 120){
      elemScroll.animate({'scrollTop': itemTop/2}, 200)
    }
  }



  //手机设备的简单适配
  var treeMobile = $('.site-tree-mobile')
  ,shadeMobile = $('.site-mobile-shade')

  treeMobile.on('click', function(){
    $('body').addClass('site-mobile');
  });

  shadeMobile.on('click', function(){
    $('body').removeClass('site-mobile');
  });

  exports('global', {});
});