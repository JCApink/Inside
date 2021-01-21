package com.test.inside.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.TodoMapper;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.pojo.Todo;
import com.test.inside.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo> implements TodoService {


    @Autowired
    private TodoMapper todoMapper;

    @Override
    public DataMap sortTodoTime(Integer userid){
        List<Todo> countDowns = todoMapper.getPlanDate(userid);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(countDowns);
        if(countDowns.size() == 0){
            return null;
        }
        return DataMap.success().setData(jsonArray);
    }

    @Override
    public boolean addTodo(Integer userid, String listTodo){
        JSONObject jsonObject = JSONObject.parseObject(listTodo);
        Todo todo = new Todo();
        todo.setUserid(userid);
        if(jsonObject.containsKey("id")){
            todo.setId(Integer.parseInt(jsonObject.getString("id")));
            todo.setAdddate(Timestamp.valueOf(jsonObject.getString("addTime")));
            todo.setFinishdate(Timestamp.valueOf(jsonObject.getString("completedTime")));
            todo.setContent(jsonObject.getString("content"));
            if(jsonObject.getString("completed").equals("true")){
                todo.setCompleted(true);
            }else{
                todo.setCompleted(false);
            }
            todoMapper.updateById(todo);
            return true;
        }else{
            todo.setAdddate(Timestamp.valueOf(jsonObject.getString("addTime")));
            todo.setFinishdate(Timestamp.valueOf(jsonObject.getString("completedTime")));
            todo.setContent(jsonObject.getString("content"));
            if(jsonObject.getString("completed").equals("true")){
                todo.setCompleted(true);
            }else{
                todo.setCompleted(false);
            }
        }
        if(todoMapper.selectAll()==null){
            todo.setId(1);
        }else{
            todo.setId(todoMapper.selectAll()+1);
        }
        todoMapper.insert(todo);
        return true;
    }

    @Override
    public boolean  deleteTodo(Integer userid, Integer id){
        if(id == null){
            return false;
        }
        todoMapper.deleteById(id);
        return true;
    }

    @Override
    public void deleteAllTodo(Integer userid){
        List<Todo> todos = todoMapper.getPlanDate(userid);
        for(int i=0; i<todos.size(); ++i){
            if(todos.get(i).isCompleted() == true){
                todoMapper.deleteById(todos.get(i));
            }
        }

    }

}
