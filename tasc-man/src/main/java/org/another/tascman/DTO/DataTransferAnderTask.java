package org.another.tascman.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class DataTransferAnderTask {
    private Long id;
    private String subtaskText;


    public DataTransferAnderTask() {
    }

    public DataTransferAnderTask(Long id, String subtaskText) {
        this.id = id;
        this.subtaskText = subtaskText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubtaskText() {
        return subtaskText;
    }

    public void setSubtaskText(String subtaskText) {
        this.subtaskText = subtaskText;
    }

    @Override
    public String toString() {
        return "DataTransferAnderTask{" +
                "id=" + id +
                ", subtaskText='" + subtaskText + '\'' +
                '}';
    }
}
