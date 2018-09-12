package com.newframe.enums.order;

/**
 * @author kfm
 * @date 2018.09.11 17:09
 */
public enum MessagePushEnum {
    // 融资购机申请
    FINANCING_APPLY(1,1,"融资购机申请","您收到一笔融资购机的申请，请尽快前往处理"),
    RENT_APPLY(2,2,"租机申请","您收到一笔租机的申请，请尽快前往处理"),
    DELIVER_APPLY(3,3,"发货申请","您收到一笔发货的申请，请尽快前往处理"),
    ;
    MessagePushEnum(Integer type,Integer roleId,String title,String content){
        this.title = title;
        this.content = content;
        this.type = type;
        this.roleId = roleId;
    }
    private String title;
    private String content;
    private Integer type;
    private Integer roleId;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getType() {
        return type;
    }

    public Integer getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return "MessagePushEnum{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", roleId=" + roleId +
                '}';
    }
}
