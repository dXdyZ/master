package org.another.tascman.DTO;

public class DataTransferTaskName {
    String oldName;
    String newName;

    public DataTransferTaskName(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }

    public DataTransferTaskName() {
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
