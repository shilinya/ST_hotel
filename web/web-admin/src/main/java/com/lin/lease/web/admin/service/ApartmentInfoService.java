package com.lin.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.lease.model.entity.ApartmentInfo;
import com.lin.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.lin.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.lin.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.lin.lease.web.admin.vo.apartment.ApartmentSubmitVo;


public interface ApartmentInfoService extends IService<ApartmentInfo> {
    //根据ApartmentSubmitVo更新公寓信息或新建一个公寓并保存信息
    void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo);

    /**
     * 根据省市区分页查询公寓
     * @param page
     * @param queryVo
     * @return
     */
    IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> page, ApartmentQueryVo queryVo);

    /**
     *根据id查询公寓详细信息
     * @param id
     * @return
     */
    ApartmentDetailVo queryApartmentById(Long id);

    /**
     * 根据公寓id删除公寓所有信息，包括图片，配套设施，杂费等等
     * @param id
     */
    void removeApartmentById(Long id);
}
