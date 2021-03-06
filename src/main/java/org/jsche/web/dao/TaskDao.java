package org.jsche.web.dao;

import org.apache.ibatis.annotations.Param;
import org.jsche.entity.KeyValuePair;
import org.jsche.entity.Task;

import java.util.List;
import java.util.Map;

/**
 * Created by Intellij IDEA. Author myan
 * Date 2017/3/29.
 */
public interface TaskDao {
	
    List<Task> getTaskByUserId(@Param("userId") int userId);

    Task getTaskById(@Param("id") int id);

    void save(Task task);

    List<Task> getIncomingTasks(@Param("userId") int userId);

    List<KeyValuePair> getWeekSerial();

    List<KeyValuePair> getWeeklyTrendData(@Param("userId") int userId);

    int getTodayTaskCount(@Param("userId") int userId);

    Map<String,Integer> getExtraData(@Param("userId") int userId);

    List<Task> getUserTaskPages(Map<String, Object> params);
}
