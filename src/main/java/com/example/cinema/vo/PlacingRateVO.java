package com.example.cinema.vo;

public class PlacingRateVO {
    private Integer movieId;
    private String name;
    private Integer audienceNum;//观众人次
    private Integer seatsNumInHalls;//影厅数*座位数
    private Integer schedulesNum;//放映场次
    private double  placingRate;//上座率

    public Integer getMovieId() {
        return movieId;
    }
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public Integer getAudienceNum() {
        return audienceNum;
    }
    public void setAudienceNum(Integer audienceNum) {
        this.audienceNum = audienceNum;
    }

    public Integer getSeatsInHalls() {
        return seatsNumInHalls;
    }
    public void setSeatsInHalls(Integer seatsNumInHalls) {
        this.seatsNumInHalls = seatsNumInHalls;
    }

    public Integer getSchedulesNum() {
        return schedulesNum;
    }
    public void setSchedulesNum(Integer schedulesNum) {
        this.schedulesNum = schedulesNum;
    }

    public double getPlacingRate() {
        return placingRate;
    }
    public void setPlacingRate(double placingRate) { this.placingRate = placingRate; }

}
