// JavaScript Document
$(document).ready(function(){

	
	$('.news-title em').mouseenter(function(){

		$(this).addClass('cur').siblings('em').removeClass('cur');
		$(this).parent().siblings("a").eq($(this).index()).show().siblings("a").hide();
		$('.zhishi-list').eq($(this).index()).show().siblings().hide();
	});
	
		
    $('.student-i i').click(function(){
		$(this).addClass('cur').siblings('i').removeClass('cur');
		$('.student-list li').eq($(this).index()).fadeIn().siblings('li').css('display','none');
	});
	
    $('.xs').click(function(){
		$(this).addClass('cur').parent("tr").siblings("tr").find("td").removeClass('cur');
		$('.joinbox').eq($(this).parent().index()-1).show().siblings('.joinbox').hide();
	});
		
    	$('.gunbus').click(function(){
		$(".tkboxs").hide();
	});	
	$('.tongsi').click(function(){
		$(".tkboxs").show();
	});			
/*****************banner********************/

   var qiehuan=$(".banner li");
   var btenr=$(".btn_btn span")
   var speed=4000;
   var nowindex=0;
   var length=qiehuan.length;
   
   
   qiehuan.eq(nowindex).fadeIn('slow').siblings().fadeOut('slow');
   
   function play(){
	   nowindex++;
	   if(nowindex>length-1){nowindex=0}
	   qiehuan.eq(nowindex).fadeIn('slow').siblings().fadeOut('slow');
	   btenr.eq(nowindex).addClass('curus').siblings().removeClass('curus');
	   }

  btenr.click(function(){
	  
	  clearInterval(tme) 
	  nowindex=$(this).index()
	  qiehuan.eq(nowindex).fadeIn('slow').siblings().fadeOut('slow');
	  btenr.eq(nowindex).addClass('curus').siblings().removeClass('curus');
	  
	  tme=setInterval(play , speed)  
	  
	  })
   $(".btnleft").click(function(){
	  
	  clearInterval(tme) 
	  nowindex=nowindex-1;
	  if(nowindex<0){nowindex=length-1}
	  qiehuan.eq(nowindex).fadeIn('slow').siblings().fadeOut('slow');
	  btenr.eq(nowindex).addClass('curus').siblings().removeClass('curus');
	  
	  tme=setInterval(play , speed)  
	  
	  })
  $(".btnright").click(function(){
	  
	  clearInterval(tme) 
	  nowindex++;
	  if(nowindex>length-1){nowindex=0}
	  qiehuan.eq(nowindex).fadeIn('slow').siblings().fadeOut('slow');
	  btenr.eq(nowindex).addClass('curus').siblings().removeClass('curus');
	  
	  tme=setInterval(play , speed)  
	  
	  })
  $(".banner").hover(function(){
	  $(".btnxsd").fadeIn();
	  },function(){
	  $(".btnxsd").fadeOut();  
   })	  	  
 
 
      tme=setInterval(play , speed) 
  
	  if($(window).width()<600){
	  $('#jump').hide();
	  }
  else{
	  
	 $(window).scroll(function() {
		if ($(window).scrollTop() > 500) {
			$('#jump').fadeIn();
		} else {
			$('#jump').fadeOut();
		}
	}); 
	  }	  
	
	$("#top").click(function() {
		$('body,html').animate({
			scrollTop: 0
		},
		500);
		return false;
	});
       $("#weixin").mouseenter(function(){
		$("#EWM").fadeIn();
		})
       $("#weixin").mouseleave(function(){
		$("#EWM").hide();
		})

	
});