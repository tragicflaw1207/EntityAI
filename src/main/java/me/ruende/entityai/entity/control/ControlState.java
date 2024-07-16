package me.ruende.entityai.entity.control;

public class ControlState {
    private static final ControlState instance = new ControlState();

    private volatile boolean movement = false;
    private volatile boolean surround = false;

    private ControlState() {}

    public static ControlState getInstance() {
        return instance;
    }

    public boolean isMovement() {
        return movement;
    }

    public boolean isSurround() {
        return surround;
    }

    public void setAttack(boolean attack) {
        if (attack) {
            setStates(false, false);
        }
    }

    public void setMovement(boolean movement) {
        if (movement) {
            setStates(true, false);
        } else {
            this.movement = false;
        }
    }

    public void setSurround(boolean surround) {
        if (surround) {
            setStates(false, true);
        } else {
            this.surround = false;
        }
    }

    private synchronized void setStates(boolean movement, boolean surround) {
        this.movement = movement;
        this.surround = surround;
    }
}
