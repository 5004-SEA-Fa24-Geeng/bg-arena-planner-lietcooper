package student;

import java.util.Comparator;

/**
 * A class used as comparator for sorting by game columns.
 */
public final class Sorts {

    /**
     * Private constructor preventing instansiation.
     */
    private Sorts() {

    }

    /**
     * The static method for sorting by game columns.
     * @param col the column of game
     * @param asc acsending or descending order
     * @return the Comparator
     */
    public static Comparator<BoardGame> sortByCol(GameData col, boolean asc) {
        return switch (col) {
            case NAME -> (o1, o2) -> {
                int compare = o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                return asc ? compare : -compare;
            };
            case RATING -> (o1, o2) -> {
                int compare = Double.compare(o1.getRating(), o2.getRating());
                return asc ? compare : -compare;
            };
            case DIFFICULTY -> (o1, o2) -> {
                int compare = Double.compare(o1.getDifficulty(), o2.getDifficulty());
                return asc ? compare : -compare;
            };
            case RANK -> (o1, o2) -> {
                int compare = Integer.compare(o1.getRank(), o2.getRank());
                return asc ? compare : -compare;
            };
            case MIN_PLAYERS -> (o1, o2) -> {
                int compare = o1.getMinPlayers() - o2.getMinPlayers();
                return asc ? compare : -compare;
            };
            case MAX_PLAYERS -> (o1, o2) -> {
                int compare = o1.getMaxPlayers() - o2.getMaxPlayers();
                return asc ? compare : -compare;
            };
            case MIN_TIME -> (o1, o2) -> {
                int compare = o1.getMinPlayTime() - o2.getMinPlayTime();
                return asc ? compare : -compare;
            };
            case MAX_TIME -> (o1, o2) -> {
                int compare = o1.getMaxPlayTime() - o2.getMaxPlayTime();
                return asc ? compare : -compare;
            };
            case YEAR -> (o1, o2) -> {
                int compare = o1.getYearPublished() - o2.getYearPublished();
                return asc ? compare : -compare;
            };
            default -> (o1, o2) -> 0;
        };
    }
}
