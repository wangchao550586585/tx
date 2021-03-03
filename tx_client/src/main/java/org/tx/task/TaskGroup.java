package org.tx.task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangChao
 * @create 2021/2/28 11:55
 */
public class TaskGroup {
    String groupId;
    private Task current;
    private List<Task> tasks ;

    public TaskGroup(String groupId) {
        this.groupId = groupId;
        tasks = new ArrayList<>();
    }

    public void setCurrent(Task current) {
        this.current = current;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getCurrent() {
        return current;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}
