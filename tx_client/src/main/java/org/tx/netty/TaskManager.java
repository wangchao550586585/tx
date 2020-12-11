package org.tx.netty;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/11 14:43
 */
public class TaskManager {
    public static ConcurrentHashMap<String, Task> map = new ConcurrentHashMap<>();

    private static class TaskManagerHolder {
        static final TaskManager INSTANCE = createSocketManager();

        private static TaskManager createSocketManager() {
            return new TaskManager();
        }
    }

    public static TaskManager instance() {
        return TaskManager.TaskManagerHolder.INSTANCE;
    }

    public Task createTask(String key) {
        Task task = new Task();
        map.put(key, task);
        return task;
    }

    public Task getTask(String key) {
        return map.get(key);
    }

}
