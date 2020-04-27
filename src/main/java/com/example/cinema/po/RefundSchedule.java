package com.example.cinema.po;

public class RefundSchedule {

    /**
     * 退票策略id
     */
    private int Id;
    /**
     * 距离电影开场最佳退票时间
     * 剩余时间大于这个时间则以最好的退票比例退钱
     * 少于这个时间则和下个时间进行比较
     */
    private double BestTime;
    /**
     * 剩余时间大于最佳退票时间时的退钱比例
     */
    private double BestPercent;
    /**
     * 距离电影开场一般退票时间
     * 剩余时间大于这个时间，少于最佳时间则以一般的退票比例退钱
     * 少于这个时间则和下个时间进行比较
     */
    private double MediumTime;
    /**
     * 剩余时间大于一般退票时间,少于最佳退票时间时的退钱比例
     */
    private double MediumPercent;
    /**
     * 距离电影开场最差的退票时间
     * 剩余时间大于这个时间，少于一般时间则以最差的退票比例退钱
     * 少于这个时间则不退钱，只退票
     */
    private double BadTime;
    /**
     * 剩余时间大于最差退票时间,少于一般退票时间时的退钱比例
     */
    private double BadPercent;

//    public RefundSchedule(int id, double bestTime, double bestPercent, double mediumTime, double mediumPercent, double badTime, double badPercent) {
//        Id = id;
//        BestTime = bestTime;
//        BestPercent = bestPercent;
//        MediumTime = mediumTime;
//        MediumPercent = mediumPercent;
//        BadTime = badTime;
//        BadPercent = badPercent;
//    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public double getBestTime() {
        return BestTime;
    }

    public void setBestTime(double bestTime) {
        BestTime = bestTime;
    }

    public double getBestPercent() {
        return BestPercent;
    }

    public void setBestPercent(double bestPercent) {
        BestPercent = bestPercent;
    }

    public double getMediumTime() {
        return MediumTime;
    }

    public void setMediumTime(double mediumTime) {
        MediumTime = mediumTime;
    }

    public double getMediumPercent() {
        return MediumPercent;
    }

    public void setMediumPercent(double mediumPercent) {
        MediumPercent = mediumPercent;
    }

    public double getBadTime() {
        return BadTime;
    }

    public void setBadTime(double badTime) {
        BadTime = badTime;
    }

    public double getBadPercent() {
        return BadPercent;
    }

    public void setBadPercent(double badPercent) {
        BadPercent = badPercent;
    }
}
