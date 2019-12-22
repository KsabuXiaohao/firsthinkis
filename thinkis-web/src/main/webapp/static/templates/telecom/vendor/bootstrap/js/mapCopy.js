 // 定义一个控件类,即function
    function ZoomControl(){
    // 默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
	    this.defaultOffset = new BMap.Size(40, 40);
    }

    ZoomControl.prototype = new BMap.Control(); // 通过JavaScript的prototype属性继承于BMap.Control

    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
	// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
	ZoomControl.prototype.initialize = function(map){
	  // 创建一个DOM元素
	  var div = document.createElement("div");
	  div.setAttribute('class', 'input-group')
	  var input = div.appendChild(document.createElement("input"))
	  input.setAttribute('typr','text')
	  input.setAttribute('class','form-control')
	  input.setAttributr('placeholder', 'Please input phone num or ')
	  // 添加DOM元素到地图中
	  map.getContainer().appendChild(div);
	  // 将DOM元素返回
	  return div;
	}
	// 创建控件
	var myZoomCtrl = new ZoomControl();
	// 添加到地图当中
	map.addControl(myZoomCtrl);