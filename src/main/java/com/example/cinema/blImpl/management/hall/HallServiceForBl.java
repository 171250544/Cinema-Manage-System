package com.example.cinema.blImpl.management.hall;

import com.example.cinema.po.Hall;

import java.util.Date;

/**
 * @author fjj
 * @date 2019/4/28 12:27 AM
 */
public interface HallServiceForBl {
    /**
     * 搜索影厅
     * @param id
     * @return
     */
    Hall getHallById(int id);

    /**
     * 在影厅中加入最后使用时间
     * @param hallId
     * @param endTime 最后排片的时间
     * @return
     */
    void insertUseTime(int hallId, Date endTime);
}
