package org.tx.task;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangChao
 * @create 2021/2/28 11:55
 */
public class TaskGroupManager {
    public static ConcurrentHashMap<String, TaskGroup> map = new ConcurrentHashMap<>();

    private static class TaskGroupManagerHolder {
        static final TaskGroupManager INSTANCE = createTaskGroupManager();

        private static TaskGroupManager createTaskGroupManager() {
            return new TaskGroupManager();
        }
    }

    public static TaskGroupManager getInstance() {
        return TaskGroupManagerHolder.INSTANCE;
    }

    public TaskGroup createTaskGroup(String groupId) {
        TaskGroup taskGroup = new TaskGroup(groupId);
        Task task = TaskManager.instance().createTask(groupId);
        taskGroup.setCurrent(task);
        taskGroup.addTask(task);
        map.put(groupId, taskGroup);
        return taskGroup;
    }

    public Task getTask(String groupId) {
        Task task = null;
        TaskGroup taskGroup = map.get(groupId);
        if (!Objects.isNull(taskGroup)) {
             task = taskGroup.getTasks().stream().filter(t -> t.getKey().equals(groupId)).findFirst().get();
        }
        return task;
    }
    public boolean isRemove(String key) {
        return TaskManager.instance().isRemove(key);
    }
}
