package com.ainutribox.module.infra.dal.mysql.db;

import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author 河南小泉山科技
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
