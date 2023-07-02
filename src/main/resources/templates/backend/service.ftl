package ${basePackageName}.${mvcServicePackageName};

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunjin.framework.base.entity.RspData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yunjin.framework.support.domain.vo.assist.ArgsGeneralWrapRequest;
import ${basePackageName}.${mvcEntityPackageName}.${module}.${classEntityName};

import java.util.List;
import java.util.Set;

public interface ${classServiceName} extends IService<${classEntityName}> {

    IPage<${classEntityName}> pageQuery(ArgsGeneralWrapRequest<${classEntityName}> wrapRequest);

    boolean create(${classEntityName} data);

    boolean update(${classEntityName} data);
}