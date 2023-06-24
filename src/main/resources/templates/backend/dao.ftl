package ${basePackageName}.${mvcMapperPackageName}.${module};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${basePackageName}.${mvcEntityPackageName}.${module}.${classEntityName};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ${classDaoName} extends BaseMapper <${classEntityName}> {

}
