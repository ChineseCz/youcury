package com.chinesecz.common.weixin.MessageEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("xml")
public class MessagePictureEntity {

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

    @XStreamAlias("MsgDataId")
    private String MsgDataId;

    @XStreamAlias("Idx")
    private String idx;



}