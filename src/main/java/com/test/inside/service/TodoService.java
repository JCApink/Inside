package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.pojo.Todo;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-14
 */
public interface TodoService extends IService<Todo> {
    public DataMap sortTodoTime(Integer userid);

    public boolean addTodo(Integer userid, String listTodo);

    public boolean deleteTodo(Integer userid, Integer id);
    public void deleteAllTodo(Integer userid);
}
