package snakegame.structs;

public class Remainder implements Cloneable {
    private int limit;//предел целочисленного значения
    private int value;//целочисленное значение

    //конструктор класса
    public Remainder(int limit, int value) {
        this.limit = limit;
        this.value = value;
    }

    //конструктор класса по целочисленному значению
    public Remainder(int value) {
        this.limit = 100;
        this.value = value;
    }

    //геттер целочисленного значения
    public int getValue() {
        return value;
    }

    //сеттер целочисленного значения с учётом границ
    public void setValue(int value) {
        this.value = Math.floorMod(value, limit);
    }

    //клон
    public Remainder copy() {
        return new Remainder(this.limit, this.value);
    }

    //клон по лимитам
    public Remainder copy(int value) {
        return new Remainder(this.limit, Math.floorMod(value, this.limit));
    }

    //конструктор копирования
    public Remainder copyAdd(int addition) {
        return new Remainder(this.limit, Math.floorMod(this.value + addition, this.limit));
    }

    //сравнение
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Remainder remainder = (Remainder) o;

        if (limit != remainder.limit) return false;
        return value == remainder.value;
    }

    //хэшкод экземпляра класса
    @Override
    public int hashCode() {
        int result = limit;
        result = 31 * result + value;
        return result;
    }

    //перевод значения полей в строковой вид
    public String toString(){
        return "" + value;
    }
}
