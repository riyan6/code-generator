package ${basePackageName}.${mvcControllerPackageName}.${module};

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yunjin.framework.base.entity.RspData;
import com.yunjin.framework.support.domain.vo.assist.ArgsGeneralWrapRequest;
import ${basePackageName}.${mvcServicePackageName}.${classServiceName};
import ${basePackageName}.${mvcEntityPackageName}.${module}.${classEntityName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(tags = "${classControllerApiTags}")
@RestController
@RequestMapping("/${classControllerApiMapping}")
@RequiredArgsConstructor
public class ${classControllerName} {

    private final ${classServiceName} service;
    
    

}