package app.ie303hotelmanagement;

import javafx.scene.control.TableRow;

class CustomTableRow extends TableRow<Room> {
    private int previousFloor = -1;

    @Override
    protected void updateItem(Room room, boolean empty) {
        super.updateItem(room, empty);

        if (room == null || empty) {
            setVisibility(false);
            return;
        }

        int currentFloor = room.getFloor();
        if (currentFloor != previousFloor) {
            previousFloor = currentFloor;
            setVisibility(false);
        } else {
            setVisibility(true);
        }
    }

    private void setVisibility(boolean visible) {
        setVisible(visible);
        setManaged(visible);
        setPrefHeight(visible ? -1 : 0);
    }
}