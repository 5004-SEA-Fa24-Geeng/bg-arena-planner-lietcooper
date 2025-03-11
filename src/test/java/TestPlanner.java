import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import student.Planner;
import student.IPlanner;
import student.GameData;


/**
 * JUnit test for the Planner class.
 *
 * Just a sample test to get you started, also using
 * setup to help out. 
 */
public class TestPlanner {
    Set<BoardGame> games;

    @BeforeEach
    public void setup() {
        games = new HashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));
    }

    @Test
    void testEmptyFilter() {
        // "",
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("").toList();
        assertEquals(8, filtered.size());
    }

    @Test
    void testEmptyAfterOperator() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name ~=").toList();
        assertEquals(8, filtered.size());
    }

    @Test
    public void testFilterNameEquals() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name == Go").toList();
        assertEquals(1, filtered.size());
        assertEquals("Go", filtered.get(0).getName());
    }

    @Test
    void testFilterNameContains() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name ~= Go").toList();
        assertEquals(4, filtered.size());
        System.out.println(filtered);
    }

    @Test
    void testFilterNameNotEquals() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name != Go").toList();
        assertEquals(7, filtered.size());
        List<BoardGame> filtered1 = planner.filter("name != Golang").toList();
        assertEquals(6, filtered1.size());
    }

    @Test
    void testFilterNameComparison() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered1 = planner.filter("name >= Go").toList();
        assertEquals(6, filtered1.size());
        planner.reset();
        List<BoardGame> filtered2 = planner.filter("name < Go").toList();
        assertEquals(2, filtered2.size());
        planner.reset();
        List<BoardGame> filtered3 = planner.filter("name > Go").toList();
        assertEquals(5, filtered3.size());
        planner.reset();
        List<BoardGame> filtered4 = planner.filter("name <= Go").toList();
        assertEquals(3, filtered4.size());

    }

    @Test
    void testReset() {
        IPlanner planner = new Planner(games);
        // filter and then reset
        List<BoardGame> filtered = planner.filter("name == go").toList();
        assertEquals(1, filtered.size());
        planner.reset();
        List<BoardGame> reseted = planner.filter("").toList();
        assertEquals(8, reseted.size());
    }

    @Test
    void testFilterMoreNames() {
        // more names
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name ~= Go, name ~=a", GameData.MIN_PLAYERS,
                false).toList();
        assertEquals(2, filtered.size());
        assertEquals("GoRami", filtered.get(0).getName());
        assertEquals("golang", filtered.get(1).getName());
    }

    @Test
    void testFilterPlayers() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("minplayers == 2").toList();
        assertEquals(4, filtered.size());
        List<BoardGame> filtered1 = planner.filter("maxplayers <= 2").toList();
        assertEquals(1, filtered1.size());
    }

    @Test
    void testFilterDifferentColumnsAndSort() {
        // two or more different columns
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter(
                "minplayers == 2, name~=go", GameData.YEAR, false).toList();
        System.out.println(filtered);
        assertEquals(3, filtered.size());
    }
}