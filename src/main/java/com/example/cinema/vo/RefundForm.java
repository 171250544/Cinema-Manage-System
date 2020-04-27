package com.example.cinema.vo;

import com.example.cinema.po.RefundSchedule;

public class RefundForm {

    /**
     * 距离电影开场最佳退票时间
     * 剩余时间大于这个时间则以最好的退票比例退钱
     * 少于这个时间则和下个时间进行比较
     */
    private double bestTime;
    /**
     * 剩余时间大于最佳退票时间时的退钱比例
     */
    private double bestPercent;
    /**
     * 距离电影开场一般退票时间
     * 剩余时间大于这个时间，少于最佳时间则以一般的退票比例退钱
     * 少于这个时间则和下个时间进行比较
     */
    private double mediumTime;
    /**
     * 剩余时间大于一般退票时间,少于最佳退票时间时的退钱比例
     */
    private double mediumPercent;
    /**
     * 距离电影开场最差的退票时间
     * 剩余时间大于这个时间，少于一般时间则以最差的退票比例退钱
     * 少于这个时间则不退钱，只退票
     */
    private double badTime;
    /**
     * 剩余时间大于最差退票时间,少于一般退票时间时的退钱比例
     */
    private double badPercent;

    public double getBestTime() {
        return bestTime;
    }

    public void setBestTime(double bestTime) {
        this.bestTime = bestTime;
    }

    public double getBestPercent() {
        return bestPercent;
    }

    public void setBestPercent(double bestPercent) {
        this.bestPercent = bestPercent;
    }

    public double getMediumTime() {
        return mediumTime;
    }

    public void setMediumTime(double mediumTime) {
        this.mediumTime = mediumTime;
    }

    public double getMediumPercent() {
        return mediumPercent;
    }

    public void setMediumPercent(double mediumPercent) {
        this.mediumPercent = mediumPercent;
    }

    public double getBadTime() {
        return badTime;
    }

    public void setBadTime(double badTime) {
        this.badTime = badTime;
    }

    public double getBadPercent() {
        return badPercent;
    }

    public void setBadPercent(double badPercent) {
        this.badPercent = badPercent;
    }

    public RefundSchedule getRefundSchedule(){
        RefundSchedule refundSchedule = new RefundSchedule();
        refundSchedule.setBadPercent(badPercent);
        refundSchedule.setBadTime(badTime);
        refundSchedule.setBestPercent(bestPercent);
        refundSchedule.setBestTime(bestTime);
        refundSchedule.setMediumPercent(mediumPercent);
        refundSchedule.setMediumTime(mediumTime);
        return refundSchedule;
    }
}
