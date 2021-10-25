package Battleship;

public class Cell {
    // 0 - free, 1 - near the ship, 2 - 6: ship on it, 7 - hit by, 8 - sink ship, 9 - ship not here
    public int condition;
    public int extra_condition_for_recovery;
    public Ship ship_type;
    public Cell() {
        this.condition = 0;
    }
    /** Method for putting cell on the screen (using when we put ocean on the screen)*/
    public void Write() {
        if (this.condition != 7 && this.condition != 8 && this.condition != 9) {
            System.out.print("| |");
        } else {
            if (this.condition == 7) {
                System.out.print("|*|");
            } else {
                if (this.condition == 8) {
                    System.out.print("|#|");
                } else {
                    System.out.print("|@|");
                }
            }
        }
    }
}
