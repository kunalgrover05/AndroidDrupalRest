package com.example.kunal.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class  ValueObject {
    @Expose
    private String value;

    public String getValue() {
        return value;
    }
}

class  TargetObject {
    @SerializedName("target_id")
    @Expose
    private String target;
}

class  BodyObject {
    @Expose
    private String value;
    @Expose
    private String format;
    @Expose
    private String summary;

    public String getValue() {
        return value+" "+format+" "+summary;
    }
}


class  CommentObject {
    @Expose
    private String status;

    @Expose
    private String cid;

    @SerializedName("last_comment_timestamp")
    @Expose
    private String time;

    @SerializedName("last_comment_name")
    @Expose
    private String name;

    @SerializedName("last_comment_uid")
    @Expose
    private String uid;

    @SerializedName("comment_count")
    @Expose
    private String count;
}

public class DrupalResponse {
    @Expose
    private List<ValueObject> nid =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> uuid =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> vid =  new ArrayList<ValueObject>();
    @Expose
    private List<TargetObject> type =  new ArrayList<TargetObject>();
    @Expose
    private List<ValueObject> langcode =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> title =  new ArrayList<ValueObject>();
    @Expose
    private List<TargetObject> uid =  new ArrayList<TargetObject>();
    @Expose
    private List<ValueObject> status =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> created =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> changed =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> promote =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> sticky =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> revision_timestamp =  new ArrayList<ValueObject>();
    @Expose
    private List<TargetObject> revision_uid =  new ArrayList<TargetObject>();
    @Expose
    private List<ValueObject> revision_log =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> path =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> field_image =  new ArrayList<ValueObject>();
    @Expose
    private List<ValueObject> field_tags =  new ArrayList<ValueObject>();
    @Expose
    private List<BodyObject> body =  new ArrayList<BodyObject>();
    @Expose
    private List<CommentObject> comment =  new ArrayList<CommentObject>();


    public String getTitle() {
        return title.get(0).getValue();
    }
    public String getBody() { return body.get(0).getValue(); }
}
