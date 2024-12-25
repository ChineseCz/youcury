package com.chinesecz.common.weixin.MessageEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("xml")
public class MessageEntity {
    @XStreamAlias("URL")
    private  String URL;
    @XStreamAlias("ToUserName")
    private String toUserName;

    @XStreamAlias("FromUserName")
    private String fromUserName;

    @XStreamAlias("CreateTime")
    private String createTime;

    @XStreamAlias("MsgType")
    private String msgType;

    @XStreamAlias("PicUrl")
    private String picUrl;

    @XStreamAlias("MediaId")
    private String mediaId;

    @XStreamAlias("MsgId")
    private String msgId;

    @XStreamAlias("MsgID")
    private String msgID;

    @XStreamAlias("MsgDataId")
    private String MsgDataId;

    @XStreamAlias("Idx")
    private String idx;

    @XStreamAlias("Event")
    private String event;

    @XStreamAlias("EventKey")
    private String eventKey;

    @XStreamAlias("Status")
    private String status;

    @XStreamAlias("Ticket")
    private String ticket;

    @XStreamAlias("Content")
    private String content;

}