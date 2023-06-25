package org.code.generator.func;

import org.code.generator.constant.SystemKey;
import org.code.generator.util.CacheUtil;

import java.util.Map;

public interface IGenerator {

    /**
     * 生成代码
     *
     * @return
     */
    void generator();

    /**
     * 供实现类快捷方便的添加数据
     *
     * @param dataModel
     * @param key
     */
    default void addDataModel(Map<String, Object> dataModel, SystemKey key) {
        dataModel.put(key.value(), CacheUtil.getInstance().get(key));
    }

}
