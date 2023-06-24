package ${basePackageName}.${mvcServiceImplPackageName};

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunjin.framework.base.entity.RspData;
import com.yunjin.framework.support.domain.vo.assist.ArgsGeneralWrapRequest;
import ${basePackageName}.${mvcEntityPackageName}.${module}.${classEntityName};
import ${basePackageName}.${mvcMapperPackageName}.${module}.${classDaoName};
import ${basePackageName}.${mvcServicePackageName}.${classServiceName};
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ${classServiceImplName} extends ServiceImpl<${classDaoName}, ${classEntityName}>
    implements ${classServiceName} {

}