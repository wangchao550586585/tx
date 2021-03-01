package org.tx.task;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/11 14:43
 */
public class TaskManager {
    public static ConcurrentHashMap<String, Task> map = new ConcurrentHashMap<>();

    public void remove(String key) {
        map.remove(key);
    }

    private static class TaskManagerHolder {
        static final TaskManager INSTANCE = createTaskManager();

        private static TaskManager createTaskManager() {
            return new TaskManager();
        }
    }

    public static TaskManager instance() {
        return TaskManager.TaskManagerHolder.INSTANCE;
    }

    public Task createTask(String key) {
        Task task = new Task(key);
        map.put(key, task);
        return task;
    }

    public Task getTask(String key) {
        return map.get(key);
    }

    public boolean isRemove(String key) {
        return map.containsKey(key);
    }


}
