package ${basePackageName}.${mvcControllerPackageName}.${module};

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yunjin.framework.base.entity.RspData;
import com.yunjin.framework.base.entity.RspDataUtil;
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

    @ApiOperation("分页查询获取 ${businessName} 列表")
    @PostMapping("/page/query")
    public RspData<IPage<${classEntityName}>> pageQuery(@RequestBody ArgsGeneralWrapRequest<${classEntityName}> request) {
        IPage<${classEntityName}> pageOut = service.pageQuery(request);
        return RspDataUtil.message(pageOut);
    }

    @ApiOperation("新增 ${businessName}")
    @PostMapping("/add")
    public RspData add(@RequestBody ${classEntityName} data) {
        boolean success = service.create(data);
        return success ? RspDataUtil.ok() : RspDataUtil.error("新增失败，请稍后再试");
    }

    @ApiOperation("修改 ${businessName}")
    @PutMapping("/edit")
    public RspData edit(@RequestBody ${classEntityName} data) {
        Assert.notNull(data.getPid(), "缺少参数");
        boolean success = service.update(data);
        return success ? RspDataUtil.ok() : RspDataUtil.error("修改失败，请稍后再试");
    }

}