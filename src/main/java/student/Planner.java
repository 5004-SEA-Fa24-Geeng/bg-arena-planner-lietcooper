package student;


import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Planner implements IPlanner {
    /** delimiter for filter string.*/
    private static final String DELIMITER = ",";

    /** Original set of games.*/
    private final Set<BoardGame> original;

    /** Filtered stream of games.*/
    private Set<BoardGame> filtered;

    /**
     * Constructor for Planner.
     * @param games the set of boardgames
     */
    public Planner(Set<BoardGame> games) {
        original = new HashSet<>(games);
        filtered = Set.copyOf(original);
    }

    @Override
    public Stream<BoardGame> filter(String filter) {
        return this.filter(filter, GameData.NAME, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return this.filter(filter, sortOn, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        String[] parts = filter.split(DELIMITER);
        for (String part: parts) {
            filtered = filterSingle(filtered.stream(), part).collect(Collectors.toSet());
        }
        return filtered.stream().sorted(Sorts.sortByCol(sortOn, ascending));
    }

    /**
     * Helper method to carry out single filter option.
     * @param toFilter the filter stream
     * @param filterStr the filter string
     * @return the filtered stream
     */
    private Stream<BoardGame> filterSingle(Stream<BoardGame> toFilter, String filterStr) {
        Operations operator = Operations.getOperatorFromStr(filterStr);
        if (operator == null) {
            return toFilter;
        }

        filterStr = filterStr.replaceAll(" ", "");
        String[] parts = filterStr.split(operator.getOperator());
        if (parts.length != 2) {
            return toFilter;
        }

        GameData col;
        try {
            col = GameData.fromString(parts[0]);
        } catch (IllegalArgumentException e) {
            return toFilter;
        }

        return toFilter.filter(o -> select(o, col, operator, parts[1]));
    }

    /**
     * Helper method to filter the stream according to the condition.
     * @param game the boardgame
     * @param col the game data column
     * @param op the operator
     * @param str the string or numeric condition
     * @return true if the codition is met; otherwise return false
     */
    private boolean select(BoardGame game, GameData col, Operations op, String str) {
        String st;  // temporary string
        double db;  // temporary double
        int num;  // temporary num
        switch (col) {
            case NAME:
                st = game.getName();;
                return switch (op) {
                    case EQUALS -> st.equalsIgnoreCase(str);
                    case CONTAINS -> st.toLowerCase().contains(str.toLowerCase());
                    case NOT_EQUALS -> !st.equalsIgnoreCase(str);
                    case GREATER_THAN -> st.compareTo(str) > 0;
                    case GREATER_THAN_EQUALS -> st.compareTo(str) >= 0;
                    case LESS_THAN ->  st.compareTo(str) < 0;
                    case LESS_THAN_EQUALS -> st.compareTo(str) <= 0;
                };
            case RATING:
                double rating = game.getRating();
                try {
                    db = Double.parseDouble(str);
                } catch (NumberFormatException e) {
                    return false;
                }
                return selectNumeric(op, db, rating);
            case DIFFICULTY:
                double difficulty = game.getDifficulty();
                try {
                    db = Double.parseDouble(str);
                } catch (NumberFormatException e) {
                    return false;
                }
                return selectNumeric(op, db, difficulty);
            case RANK:
                int rank = game.getRank();
                try {
                    num = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    return false;
                }
                return selectNumeric(op, num, rank);
            case MIN_PLAYERS:
                int minp = game.getMinPlayers();
                try {
                    num = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    return false;
                }
                return selectNumeric(op, num, minp);
            case MAX_PLAYERS:
                int maxp = game.getMaxPlayers();
                try {
                    num = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    return false;
                }
                return selectNumeric(op, num, maxp);
            case MIN_TIME:
                int mint = game.getMinPlayTime();
                try {
                    num = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    return false;
                }
                return selectNumeric(op, num, mint);
            case MAX_TIME:
                int maxt = game.getMaxPlayTime();
                try {
                    num = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    return false;
                }
                return selectNumeric(op, num, maxt);
            case YEAR:
                int year = game.getYearPublished();
                try {
                    num = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    return false;
                }
                return selectNumeric(op, num, year);
            default:
                return false;
        }
    }

    /**
     * Filter for integer conditions.
     * @param op the operator
     * @param num the integer
     * @param value the column value
     * @return true if the condition is met
     */
    private boolean selectNumeric(Operations op, int num, int value) {
        return switch (op) {
            case EQUALS -> value == num;
            case NOT_EQUALS -> value != num;
            case GREATER_THAN -> value > num;
            case LESS_THAN -> value < num;
            case GREATER_THAN_EQUALS -> value >= num;
            case LESS_THAN_EQUALS -> value <= num;
            default -> false;
        };
    }

    /**
     * Filter for double conditions.
     * @param op the operator
     * @param num the double
     * @param value the column value
     * @return true if the condition is met
     */
    private boolean selectNumeric(Operations op, double num, double value) {
        return switch (op) {
            case EQUALS -> value == num;
            case NOT_EQUALS -> value != num;
            case GREATER_THAN -> value > num;
            case LESS_THAN -> value < num;
            case GREATER_THAN_EQUALS -> value >= num;
            case LESS_THAN_EQUALS -> value <= num;
            default -> false;
        };
    }

    @Override
        public void reset() {
            filtered = Set.copyOf(original);
        }

    }
