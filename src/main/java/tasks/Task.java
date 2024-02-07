package tasks;

import java.time.format.DateTimeFormatter;

public class Task {
    protected String description;
    protected boolean isDone;

    protected DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm");
    public Task(String description){
        this.description = description;
        this.isDone = false;
    }

    public Task() {

    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), this.description);
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public String getSaveLine() { return "Unknown Task Type";}
}
