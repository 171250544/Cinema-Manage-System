package com.example.cinema.bl.management;

import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
public interface HallService {
    /**
     * 搜索所有影厅
     * @return
     */
    ResponseVO searchAllHall();

    /***
     * 新增影厅
     * @param hallForm
     * @return
     */
    ResponseVO insertHall(HallForm hallForm);

    /***
     * 查询影厅使用状态
     * @return
     */
    ResponseVO canChangeHall();

    /***
     * 更改影厅信息
     * @param hallVO
     * @return
     */
    ResponseVO changeInfo(HallVO hallVO);
}
