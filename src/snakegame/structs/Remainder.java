package snakegame.structs;

public class Remainder implements Cloneable {
    private int limit;
    private int value;

    public Remainder(int limit, int value) {
        this.limit = limit;
        this.value = value;
    }

    public Remainder(int value) {
        this.limit = 100;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value % limit;
    }

    public Remainder copy() {
        return new Remainder(this.limit, this.value);
    }

    public Remainder copy(int value) {
        return new Remainder(this.limit, value % this.limit);
    }

    public Remainder copyAdd(int addition) {
        return new Remainder(this.limit, (this.value + addition) % this.limit);
    }
}
