package ${basePackageName}.${mvcServiceImplPackageName};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunjin.framework.base.entity.RspData;
import com.yunjin.framework.support.domain.vo.assist.ArgsGeneralRequest;
import com.yunjin.framework.support.domain.vo.assist.ArgsGeneralWrapRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.hutool.core.util.IdUtil;
import ${basePackageName}.${mvcEntityPackageName}.${module}.${classEntityName};
import ${basePackageName}.${mvcMapperPackageName}.${module}.${classDaoName};
import ${basePackageName}.${mvcServicePackageName}.${classServiceName};
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ${classServiceImplName} extends ServiceImpl<${classDaoName}, ${classEntityName}>
    implements ${classServiceName} {

    private final ${classDaoName} mapper;

    @Override
    public IPage<${classEntityName}> pageQuery(ArgsGeneralWrapRequest<${classEntityName}> wrapRequest) {
        // 查询条件
        QueryWrapper<${classEntityName}> queryWrapper = new QueryWrapper<>();

        // 默认不查询逻辑删除的数据
        queryWrapper.eq("isdel", 0);

        // 根据前端条件构建Sql查询条件
        ArgsGeneralRequest[] generalRequestArr = wrapRequest.getGeneralRequest();
        for (ArgsGeneralRequest generalRequest : generalRequestArr) {
            String columnName = generalRequest.getColumnName();
            Object columnValue = generalRequest.getColumnValue()[0];
            // 精确查询
            queryWrapper.eq(columnName, columnValue);
        }

        // 查询出的结果，默认按照 排序码seq 升序查询
        queryWrapper.orderByAsc("seq");

        // 构建分页条件
        Page<${classEntityName}> pageIn = new Page<>(
            wrapRequest.getAssistRequest().getPageIndex().intValue(),
            wrapRequest.getAssistRequest().getPageNum().intValue()
        );

        // 进行sql查询
        IPage<${classEntityName}> pageOut = mapper.selectPage(pageIn, queryWrapper);
        return pageOut;
    }

    @Override
    public boolean create(${classEntityName} data) {
        setField(data);
        int changeCount = mapper.insert(data);
        return changeCount > 0;
    }

    @Override
    public boolean update(${classEntityName} data) {
        // 最后操作人信息
        data.setLastoperator(68392L);
        data.setLastoperatetime(new Date());
        int changeCount = mapper.updateById(data);
        return changeCount > 0;
    }

    /**
    * 统一设置规则项的某些字段
    *
    * @param data
    */
    private void setField(${classEntityName} data) {
        // 随机生成id 使用雪花算法id
        long randomId = IdUtil.getSnowflake().nextId();
        Date date = new Date();
        // 设置实体类没有的属性；剩余未填充字段：tenantid、tid
        data.setPid(randomId);
        // 是否删除
        data.setIsdel(0);
        // 创建人信息
        data.setCreator(68392L);
        data.setCreatetime(date);
        // 最后操作人信息
        data.setLastoperator(68392L);
        data.setLastoperatetime(date);
    }
}