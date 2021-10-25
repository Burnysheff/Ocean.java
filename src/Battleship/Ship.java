package Battleship;

public class Ship {
    public String name;
    public int size;
    public int injuries;
    public int index;
    /** Constructor, where we initialise the ship */
    public Ship(String name, int size, int index) {
        this.name = name;
        this.size = size;
        this.index = index;
    }
}
