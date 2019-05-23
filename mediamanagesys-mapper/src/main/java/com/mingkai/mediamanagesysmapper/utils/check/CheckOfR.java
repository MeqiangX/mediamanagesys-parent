package com.mingkai.mediamanagesysmapper.utils.check;

import com.dtstack.plat.lang.base.Strings;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-23 20:57
 */
public class CheckOfR {
    private static final Logger log = LoggerFactory.getLogger(CheckOfR.class);

    /**
     * 检查返回结果R<T>并处理
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> T check(R<T> result) {
        if (Objects.isNull(result)) {
            throw new BizException("系统错误，无返回结果！");
        }
        if (!result.isSuccess()) {
            if (log.isDebugEnabled()) {
                log.debug(Strings.format("返回结果R：{}, message:{}", result.isSuccess(), result.getMessage()));
            }
            throw new BizException(result.getMessage());
        }
        return result.getData();
    }

}
