package com.thinkis.modules.wx.dto;

/**
 * 公众号菜单的枚举类
 *
 * @author Binary Wang
 */
public enum WxMenuEnum {

  WX_MENU_COOPERATION("cooperation", "合作","邮箱：383516619@qq.com"),
  WX_MENU_SHARE("share", "分享","分享：http://www.ithinkis.cn/home/list-96f40532c611490e90b8ef89cb4001c5.html"),
  WX_MENU_COMMUNITY("community", "社区","我的社区：http://www.ithinkis.cn/home/index-fly.html"),
  WX_MENU_CASE("case", "案例","案例：http://www.ithinkis.cn/home/list-2cf481cc375d4f84b0929f3f6a76e2ce.html");

  private String key;
  private String name;
  private String value;

  private WxMenuEnum(String key, String name,String value) {
    this.name = name;
    this.key = key;
    this.value = value;
  }


  public static String getName(String key) {
    for (WxMenuEnum e : values()) {
      if (e.getKey().equals(key)) {
        return e.getName();
      }
    }
    return null;
  }

  public static String getValue(String key) {
    for (WxMenuEnum e : values()) {
      if (e.getKey().equals(key)) {
        return e.getValue();
      }
    }
    return null;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
