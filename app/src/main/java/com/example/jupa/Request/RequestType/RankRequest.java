package com.example.jupa.Request.RequestType;

import com.example.jupa.Request.RequestApplicationObject;

public class RankRequest extends RequestApplicationObject {


    int group_id;

    int rank_id;

    public RankRequest(int candidate_id, String regNo, String experience, String qualification, String reason,int group_id, int rank_id) {
        super(candidate_id, regNo, experience, qualification, reason);
        this.rank_id = rank_id;
        this.group_id = group_id;
    }


    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getRank_id() {
        return rank_id;
    }

    public void setRank_id(int rank_id) {
        this.rank_id = rank_id;
    }
}
