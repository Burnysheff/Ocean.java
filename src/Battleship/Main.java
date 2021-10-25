package Battleship;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    /** A method, where we check if some ships simply can not fit into the ocean, for example
     * carrier (5 cells) can not fit in ocean 4 * 4
     * @param ocean Is a param of or ocean
     * @param sizes An array of how many ships of each size have
     * @param object An array, where every ship is an element, value is unique index of the ship
     * @return if ships can be place in the ocean*/
    public static boolean Check_size_first_step(Ocean ocean, int[] sizes, Ship[] object) {
        for (int i = 0; i < 5; ++i) {
            if (ocean.length < (i + 1) && ocean.width < (i + 1)) {
                if (sizes[4 - i] != 0) {
                    return false;
                }
            }
        }
        int index = 0;
        for (int i = 0; i < 5; ++i) {
            if (sizes[i] != 0) {
                index = i;
                break;
            }
        }
        return Check_size_second_step(ocean, sizes, object, index);
    }

    /** A method, where we check, if the ship can be place in given coordinates
     * @param direction Is a param, that shows if the ship is places is vertically or horizontally
     * @param coordinate_x A line, where the head of the ship is situated
     * @param coordinate_y A column, where the head of the ship is situated
     * @param ocean Just our ocean
     * @param index Index which literally shows the size of the ship (index cause there is an array, where
     *              ships of one size are in the element with certain index)
     * @return If ship can be places*/
    public static boolean Check_size_for_ship(int direction, int coordinate_x, int coordinate_y, Ocean ocean, int index) {
        if (direction == 0) {
            for (int j = coordinate_x; j < coordinate_x + (5 - index); ++j) {
                if (ocean.field[j][coordinate_y].condition != 0) {
                    return false;
                }
            }
        } else {
            for (int j = coordinate_y; j < coordinate_y + (5 - index); ++j) {
                if (ocean.field[coordinate_x][j].condition != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /** A Method where we are putting ship into the ocean vertically (we are changing cells and ships attributes)
     * @param coordinate_x A line, where the head of the ship is situated
     * @param coordinate_y A column, where the head of the ship is situated
     * @param index Index which literally shows the size of the ship (index cause there is an array, where
     *      *              ships of one size are in the element with certain index)
     * @param ocean Just our ocean
     * @param object An array, where every ship is an element, value is unique index of the ship
     * @param index_ship A unique index of ship we are putting in
     * @return changes ocean*/
    public static int Place_ship_vertical(int coordinate_x, int coordinate_y, int index, Ocean ocean, Ship[] object, int index_ship) {
        ocean.field[coordinate_x - 1][coordinate_y + 1].condition = 1;
        ocean.field[coordinate_x - 1][coordinate_y].condition = 1;
        ocean.field[coordinate_x - 1][coordinate_y - 1].condition = 1;
        for (int j = coordinate_x; j < coordinate_x + (5 - index); ++j) {
            ocean.field[j][coordinate_y + 1].condition = 1;
            ocean.field[j][coordinate_y].condition = 5 - index + 1;
            ocean.field[j][coordinate_y].ship_type = object[index_ship];
            ocean.field[j][coordinate_y - 1].condition = 1;
        }
        ocean.field[coordinate_x + (5 - index)][coordinate_y + 1].condition = 1;
        ocean.field[coordinate_x + (5 - index)][coordinate_y].condition = 1;
        ocean.field[coordinate_x + (5 - index)][coordinate_y - 1].condition = 1;
        ++index_ship;
        return index_ship;
    }

    /** A Method where we are putting ship into the ocean horizontally (we are changing cells and ships attributes)
     * @param coordinate_x A line, where the head of the ship is situated
     * @param coordinate_y A column, where the head of the ship is situated
     * @param index Index which literally shows the size of the ship (index cause there is an array, where
     *      *              ships of one size are in the element with certain index)
     * @param ocean Just our ocean
     * @param object An array, where every ship is an element, value is unique index of the ship
     * @param index_ship A unique index of ship we are putting in
     * @return changes ocean*/
    public static int Place_ship_horizonatal(int coordinate_x, int coordinate_y, int index, Ocean ocean, Ship[] object, int index_ship) {
        ocean.field[coordinate_x - 1][coordinate_y - 1].condition = 1;
        ocean.field[coordinate_x][coordinate_y - 1].condition = 1;
        ocean.field[coordinate_x + 1][coordinate_y - 1].condition = 1;
        for (int j = coordinate_y; j < coordinate_y + (5 - index); ++j) {
            ocean.field[coordinate_x - 1][j].condition = 1;
            ocean.field[coordinate_x][j].condition = 5 - index + 1;
            ocean.field[coordinate_x][j].ship_type = object[index_ship];
            ocean.field[coordinate_x + 1][j].condition = 1;
        }
        ocean.field[coordinate_x - 1][coordinate_y + (5 - index)].condition = 1;
        ocean.field[coordinate_x][coordinate_y + (5 - index)].condition = 1;
        ocean.field[coordinate_x + 1][coordinate_y + (5 - index)].condition = 1;
        ++index_ship;
        return index_ship;
    }

    /** A method were are putting all the ships into the sea and look if is it possible
     * We have 100000 tries to do this (one try for the ship), otherwise we will tell it is impossible
     * @param ocean Just out ocean
     * @param sizes An array of how many ships of each size have
     * @param object An array, where every ship is an element, value is unique index of the ship
     * @param index Index which literally shows the size of the ship (index cause there is an array, where
     *      *              ships of one size are in the element with certain index)
     * @return true and changed ocean if we succeeded; false if not*/
    public static boolean Check_size_second_step(Ocean ocean, int[] sizes, Ship[] object, int index) {
        int index_ship = 0;
        for (int i = 0; i < 100000; ++i) {
            while (index != 5 && sizes[index] == 0) {
                ++index;
            }
            if (index == 5) {
                return true;
            }
            int direction = (int)(Math.random() * 2);
            int coordinate_x = (int)(Math.random() * (ocean.length + 1));
            if (coordinate_x == 0) {
                ++coordinate_x;
            }
            if ((coordinate_x + 5 - index > ocean.length + 1) && direction == 0) {
                continue;
            }
            int coordinate_y = (int)(Math.random() * (ocean.width + 1));
            if (coordinate_y == 0) {
                ++coordinate_y;
            }
            if ((coordinate_y + 5 - index > ocean.width + 1) && direction == 1) {
                continue;
            }
            if (Check_size_for_ship(direction, coordinate_x, coordinate_y, ocean, index)) {
                if (direction == 0) {
                    index_ship = Place_ship_vertical(coordinate_x, coordinate_y, index, ocean, object, index_ship);
                    if (sizes[index] != 1) {
                        --sizes[index];
                    } else {
                        ++index;
                    }
                } else {
                    index_ship = Place_ship_horizonatal(coordinate_x, coordinate_y, index, ocean, object, index_ship);
                    if (sizes[index] != 1) {
                        --sizes[index];
                    } else {
                        ++index;
                    }
                }
            }
        }
        return false;
    }

    /** A Method where we check a string to be a integer
     * @param s Given string
     * @return true if integer; false if not*/
    private static boolean CheckInt(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

     /** Method where we form our ocean and check all the data from user to be correct
      * @return our ocean object*/
    public static Ocean Formation() {
        Scanner in = new Scanner(System.in);
        System.out.println("\nEnter length of the ocean:");
        String check = in.next();
        while (!CheckInt(check) || Integer.parseInt(check) <= 0) {
            System.out.println("Enter the integer number above 0 please!");
            check = in.next();
        }
        int length = Integer.parseInt(check);
        System.out.println("\nEnter width of the ocean:");
        check = in.next();
        while (!CheckInt(check) || Integer.parseInt(check) <= 0) {
            System.out.println("Enter the integer number above 0 please!");
            check = in.next();
        }
        int width = Integer.parseInt(check);
        return new Ocean(length, width);
    }

    /** Method, where we form arrays of ships (one, where they are grouped by size, another where every of them has
     * unique index). Also check users data to be correct
     * @param ships_quantity An array, where ships are grouped by size
     * @param ship_types An array with names of the ships
     * @return An array, where every ship has unique index*/
    public static Ship[] Formation(int[] ships_quantity, String[] ship_types) {
        int quantity = 0;
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < 5; ++i) {
            System.out.println("\nEnter the number of " + ship_types[i] + " (" + (5 - i) + " cells)");
            String check = in.next();
            while (!CheckInt(check) || Integer.parseInt(check) < 0) {
                System.out.println("Enter the integer number above -1 please!");
                check = in.next();
            }
            ships_quantity[i] = Integer.parseInt(check);
            quantity += ships_quantity[i];
        }
        Ship[] objects = new Ship[quantity];
        int index = 0;
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < ships_quantity[i]; ++j) {
                if (i == 0) {
                    objects[index] = new Ship("carrier", 5, index);
                    ++index;
                    continue;
                }
                if (i == 1) {
                    objects[index] = new Ship("battleship", 4, index);
                    ++index;
                    continue;
                }
                if (i == 2) {
                    objects[index] = new Ship("cruiser", 3, index);
                    ++index;
                    continue;
                }
                if (i == 3) {
                    objects[index] = new Ship("destroyer", 2, index);
                    ++index;
                    continue;
                }
                objects[index] = new Ship("submarine", 1, index);
                ++index;
            }
        }
        return objects;
    }

    /** Method, where we recovery ships after users miss (if ship recovery mode is on)
     * @param ocean Just out ocean
     * @param ship Ship, which we hit with new hot (should not be recovered)
     * @return changed ocean*/
    public static void Recovery(Ocean ocean, Ship ship) {
        boolean recovery = false;
        for (int i = 0; i < ocean.length + 2; ++i) {
            for (int j = 0; j < ocean.width + 2; ++j) {
                if ((ship == null && ocean.field[i][j].condition == 7) ||
                        (ship != null && ocean.field[i][j].condition == 7 && (ocean.field[i][j].ship_type.index != ship.index))) {
                    ocean.field[i][j].condition = ocean.field[i][j].extra_condition_for_recovery;
                    ocean.field[i][j].ship_type.injuries = 0;
                    recovery = true;
                }
            }
        }
        if (recovery) {
            System.out.println("\nOps! The ship has recovered!\n");
        }
    }

    /** A method, where we tell user that he missed and why (tell about meaning of the cells). Also here tarts recovery
     * @param ocean Just out ocean
     * @param coor_x Line of shot
     * @param coor_y Column of shot
     * @param quantity Number of ships left
     * @param recovery Tells us if recovery mode is on
     * @return Number of ships left*/
    public static int Missed_shot(int coor_x, int coor_y, Ocean ocean, int quantity, boolean recovery) {
        if (ocean.field[coor_x][coor_y].condition == 9) {
            System.out.println("\nIt's no ship here! (@ symbol means no ship!)");
            if (recovery) {
                Recovery(ocean, null);
            }
            return quantity;
        }
        if (ocean.field[coor_x][coor_y].condition == 7) {
            System.out.println("\nYes, it is ship here, but you need to destroy it all! (* means injury)");
            if (recovery) {
                Recovery(ocean, null);
            }
            return quantity;
        }
        if (ocean.field[coor_x][coor_y].condition == 8) {
            System.out.println("\nYou hit an already sunk ship (# means destroyed ship)");
            if (recovery) {
                Recovery(ocean, null);
            }
            return quantity;
        }
        if (ocean.field[coor_x][coor_y].condition == 0 || ocean.field[coor_x][coor_y].condition == 1) {
            System.out.println("\nYou missed! Take another shot!");
            ocean.field[coor_x][coor_y].condition = 9;
            if (recovery) {
                Recovery(ocean, null);
            }
            return quantity;
        }
        return quantity;
    }

    /** A method where we act with users shots
     * @param recovery Tells us if recovery mode is on
     * @param quantity Number of ships left
     * @param coor_y Column of users shot
     * @param coor_x Line of users shot
     * @param ocean Just out ocean
     * @param torpedo Tells us if torpedo mode is on
     * @return the number of ships left and a changed ocean*/
    public static int Shoot(int coor_x, int coor_y, Ocean ocean, int quantity, boolean torpedo, boolean recovery) {
        if (coor_x == 0 && coor_y == 0) {
            return quantity;
        }
        if (ocean.field[coor_x][coor_y].condition == 9 || ocean.field[coor_x][coor_y].condition == 7 ||
                ocean.field[coor_x][coor_y].condition == 8 || ocean.field[coor_x][coor_y].condition == 0
                || ocean.field[coor_x][coor_y].condition == 1) {
            return Missed_shot(coor_x, coor_y, ocean, quantity, recovery);
        }
        if (ocean.field[coor_x][coor_y].condition > 1 && ocean.field[coor_x][coor_y].condition < 7) {
            ++ocean.field[coor_x][coor_y].ship_type.injuries;
            Recovery(ocean, ocean.field[coor_x][coor_y].ship_type);
            if ((ocean.field[coor_x][coor_y].ship_type.injuries < ocean.field[coor_x][coor_y].ship_type.size)
                    && !torpedo) {
                System.out.println("\nYou hit the ship!");
                ocean.field[coor_x][coor_y].extra_condition_for_recovery = ocean.field[coor_x][coor_y].condition;
                ocean.field[coor_x][coor_y].condition = 7;
                return quantity;
            } else {
                for (int i = 0; i < ocean.length + 2; ++i) {
                    for (int j = 0; j < ocean.width + 2; ++j) {
                        if (ocean.field[i][j].ship_type != null) {
                            if (ocean.field[i][j].ship_type.index == ocean.field[coor_x][coor_y].ship_type.index) {
                                ocean.field[i - 1][j - 1].condition = 9;
                                ocean.field[i][j - 1].condition = 9;
                                ocean.field[i][j + 1].condition = 9;
                                ocean.field[i - 1][j].condition = 9;
                                ocean.field[i + 1][j].condition = 9;
                                ocean.field[i - 1][j + 1].condition = 9;
                                ocean.field[i + 1][j - 1].condition = 9;
                                ocean.field[i + 1][j + 1].condition = 9;
                            }
                        }
                    }
                }
                for (int i = 0; i < ocean.length + 2; ++i) {
                    for (int j = 0; j < ocean.width + 2; ++j) {
                        if (ocean.field[i][j].ship_type != null) {
                            if (ocean.field[i][j].ship_type.index == ocean.field[coor_x][coor_y].ship_type.index) {
                                ocean.field[i][j].condition = 8;
                            }
                        }
                    }
                }
                System.out.println("\nYou have destroyed a " + ocean.field[coor_x][coor_y].ship_type.name + "!");
                --quantity;
                return quantity;
            }
        }
        return quantity;
    }

    /** Method where we ask user if to tern on recovery mode
     * @param in Scanner to read data
     * @return true if mode is on; false is not*/
    public static boolean Set_recovery(Scanner in) {
        System.out.println("\nWould you like to play with ship recovery?");
        System.out.println("\nPress '1' as yes");
        System.out.println("\nPress '2' as no");
        String check = in.next();
        while (!CheckInt(check) || Integer.parseInt(check) < 1 || Integer.parseInt(check) > 2) {
            System.out.println("Please pick one of the variants!");
            check = in.next();
        }
        int switcher = Integer.parseInt(check);
        boolean recovery = false;
        if (switcher == 1) {
            return true;
        }
        return false;
    }

    /** Method where we build our ocean (put ships into it, tell user about some issues and
     * ask him to change something in that case)
     * @param in Scanner to read data
     * @param ocean Just our ocean
     * @param ships_quantity An array, where ships are grouped by size
     * @param ship_types An array with names of the ships
     * @return out ocean*/
    public static void Build_field(Scanner in, Ocean ocean, int[] ships_quantity, String[] ship_types) {
        String check;
        System.out.println("\nShips don't fit the ocean!");
        System.out.println("\nPress '1' to change ocean size");
        System.out.println("\nPress '2' to change ships quantity");
        System.out.println("\nPress '3' to quit the game");
        check = in.next();
        while (!CheckInt(check) || Integer.parseInt(check) < 1 || Integer.parseInt(check) > 3) {
            System.out.println("Please pick one of the variants!");
            check = in.next();
        }
        int switcher = Integer.parseInt(check);
        if (switcher == 3) {
            System.out.println("\nGame is over!");
            return;
        }
        switch (switcher) {
            case 1 -> ocean = Formation();
            case 2 -> Formation(ships_quantity, ship_types);
        }
    }

    /** Method where we ask user if to tern on torpedo mode
     * @param in Scanner to read data
     * @return number of torpedoes if mode is on; 0 is not*/
    public static int Torpedoes(Scanner in) {
        System.out.println("\nEnter the number of torpedoes (0 if you don't want play with them)");
        String check = in.next();
        while (!CheckInt(check) || Integer.parseInt(check) < 0 ) {
            System.out.println("Please enter a correct number (0<=)!");
            check = in.next();
        }
        return Integer.parseInt(check);
    }

    public static void main(String[] args) {
        String[] ship_types = {"carriers", "battleships", "cruisers", "destroyers", "submarines"};
        int[] ships_quantity = new int[5];
        Scanner in = new Scanner(System.in);
        Ocean ocean = Formation();
        Ship[] objects = Formation(ships_quantity, ship_types);
        int quantity = 0;
        for (int i = 0; i < 5; ++i) {
            quantity += ships_quantity[i];
        }
        boolean recovery = Set_recovery(in);
        String check;
        while (!Check_size_first_step(ocean, Arrays.copyOf(ships_quantity, ship_types.length), objects)) {
            Build_field(in, ocean, ships_quantity, ship_types);
        }
        for (int i = 0; i < 100; ++i) {
            System.out.println();
        }
        int torpedoes = Torpedoes(in);
        int coordinate_x = -1;
        int coordinate_y = -1;
        int shots_number = 0;
        boolean torpedo_mode;
        while (quantity > 0) {
            torpedo_mode = false;
            ocean.Write();
            if (torpedoes > 0) {
                System.out.println("\nFor firing a torpedo enter 'T' before a number of line (like: T 6)");
            }
            System.out.println("\nIf you wanna quit, enter 0 as line!");
            System.out.println("\nEnter the line of shoot:");
            check = in.next();
            if (Objects.equals(check, "T")) {
                if (torpedoes > 0) {
                    System.out.println("\nTorpedo mode activated!");
                    torpedo_mode = true;
                    --torpedoes;
                } else {
                    System.out.println("\nYou ran out of torpedoes!");
                }
            }
            while (!CheckInt(check) || Integer.parseInt(check) < 0 || Integer.parseInt(check) > ocean.length) {
                if (!Objects.equals(check, "T")) {
                    System.out.println("Enter the integer number above -1 and less that ocean size please!");
                }
                check = in.next();
            }
            coordinate_x = Integer.parseInt(check);
            if (coordinate_x == 0) {
                System.out.println("\nThank you for the play!");
                break;
            }
            System.out.println("\nEnter the column of shoot:");
            check = in.next();
            while (!CheckInt(check) || Integer.parseInt(check) <= 0 || Integer.parseInt(check) > ocean.width) {
                System.out.println("\nEnter the integer number above 0 and less that ocean size please!");
                check = in.next();
            }
            for (int i = 0; i < 5; ++i) {
                System.out.println();
            }
            coordinate_y = Integer.parseInt(check);
            quantity = Shoot(coordinate_x, coordinate_y, ocean, quantity, torpedo_mode, recovery);
            ++shots_number;
        }
        System.out.println("\nFinal ocean condition:\n");
        ocean.Write();
        if (quantity == 0) {
            System.out.println("\nCongratulations! You have destroyed all the ships!");
        }
        System.out.println("\nYou have made " + shots_number + " shots overall");
    }
}
