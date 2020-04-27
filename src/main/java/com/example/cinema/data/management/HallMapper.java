package com.example.cinema.data.management;

import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.HallVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
@Mapper
public interface HallMapper {
    /**
     * 查询所有影厅信息
     * @return
     */
    List<Hall> selectAllHall();

    /**
     * 根据id查询影厅
     * @return
     */
    Hall selectHallById(@Param("hallId") int hallId);

    /**
     *  新增影厅
     */
    int insertHall(HallForm hallForm);

    /**
     * 在影厅中加入最后使用时间
     */
    int updateUseTime(@Param("hallId") int hallId, @Param("endTime") Date endTime);

    /**
     * 更新hall的信息
     */
    int updateHallInfo(HallVO hallVO);
}
