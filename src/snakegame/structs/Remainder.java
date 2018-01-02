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
        this.value = Math.floorMod(value, limit);
    }

    public Remainder copy() {
        return new Remainder(this.limit, this.value);
    }

    public Remainder copy(int value) {
        return new Remainder(this.limit, Math.floorMod(value, this.limit));
    }

    public Remainder copyAdd(int addition) {
        return new Remainder(this.limit, Math.floorMod(this.value + addition, this.limit));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Remainder remainder = (Remainder) o;

        if (limit != remainder.limit) return false;
        return value == remainder.value;
    }

    @Override
    public int hashCode() {
        int result = limit;
        result = 31 * result + value;
        return result;
    }

    public String toString(){
        return "" + value;
    }
}
