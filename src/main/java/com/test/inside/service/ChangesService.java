package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.inside.model.pojo.Changes;
import com.test.inside.model.pojo.Changes_array;
import com.test.inside.model.pojo.Sentence;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-08-25
 */
public interface ChangesService extends IService<Changes> {

    Boolean subChanges(Integer userid, List<Changes_array> changes_arrays);

    Map getChanges(Integer userid);
}
