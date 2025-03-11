package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameListTest {
    Set<BoardGame> games;
    IGameList list;

    @BeforeEach
    void setUp() {
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
    void count() {
        IGameList list = new GameList();
        list.addToList("All", games.stream());
        assertEquals(8, list.count());
    }

    @Test
    void getGameNames() {
        IGameList list = new GameList();
        list.addToList("17 days", games.stream());
        list.addToList("Chess", games.stream());
        List<String> gameNames = list.getGameNames();
        assertEquals(gameNames.size(), list.count());
    }

    @Test
    void clear() {
        IGameList list = new GameList();
        list.addToList("All", games.stream());
        assertEquals(8, list.count());
        list.clear();
        assertEquals(0, list.count());
    }

    @Test
    void addToListByName() {
        IGameList list = new GameList();
        list.addToList("Tucano", games.stream());
        assertEquals(1, list.count());
        assertEquals("Tucano", list.getGameNames().get(0));
        // throw exception if add failed
        assertThrows(IllegalArgumentException.class, () -> list.addToList("17days", games.stream()));
        System.out.println(list.getGameNames());
    }

    @Test
    void testAddSingleGameToListByIndex() {
        IGameList list = new GameList();
        list.addToList("1", games.stream());
        assertEquals(1, list.count());
        System.out.println(list.getGameNames());
    }

    @Test
    void testAddAll() {
        IGameList list = new GameList();
        list.addToList("All", games.stream());
        assertEquals(8, list.count());
        System.out.println(list.getGameNames());
    }

    @Test
    void testAddGameByRange() {
        IGameList list = new GameList();
        // a range within the range
        list.addToList("1-3", games.stream());
        assertEquals(3, list.count());
        // 1-1 type range
        list.addToList("4-4", games.stream());
        assertEquals(4, list.count());
        // a range partially out of range
        list.addToList("1-10", games.stream());
        assertEquals(8, list.count());
    }

    @Test
    void testRemoveByName() {
        IGameList list = new GameList();
        list.addToList("1-10", games.stream());
        assertEquals(8, list.count());
        // remove
        list.removeFromList("Tucano");
        assertEquals(7, list.count());
    }

    @Test
    void testRemoveNonExistGame() {
        IGameList list = new GameList();
        list.addToList("1-10", games.stream());
        assertEquals(8, list.count());
        // remove
        list.removeFromList("Tucano");
        assertEquals(7, list.count());
        // throw exception
        assertThrows(IllegalArgumentException.class,
                () -> list.removeFromList("non-exist game"));
    }

    @Test
    void testRemoveByNumber() {
        IGameList list = new GameList();
        list.addToList("1-10", games.stream());
        assertEquals(8, list.count());
        // remove by number
        list.removeFromList("5");
        assertEquals(7, list.count());
        // remove by range
        list.removeFromList("1-4");
        assertEquals(3, list.count());
    }

    @Test
    void testRemoveByNumberOutOfRange() {
        IGameList list = new GameList();
        list.addToList("1-10", games.stream());
        assertEquals(8, list.count());
        // remove by number out of range
        assertThrows(IllegalArgumentException.class, () -> list.removeFromList("10"));
        assertEquals(8, list.count());

    }

    @Test
    void testRemoveByRangePartiallyOutOfRange() {
        IGameList list = new GameList();
        list.addToList("1-10", games.stream());
        assertEquals(8, list.count());

        // remove by range totally out of range
        assertThrows(IllegalArgumentException.class,
                () -> list.removeFromList("10-20"));
        assertEquals(8, list.count());

        // remove by range partially out of range
        list.removeFromList("4-10");
        assertEquals(3, list.count());
    }

    @Test
    void testRemoveFromEmptyList() {
        IGameList list = new GameList();
        list.addToList("1-10", games.stream());
        assertEquals(8, list.count());

        // remove all
        list.removeFromList("All");
        assertEquals(0, list.count());

        // remove all again - no exception
        list.removeFromList("All");
        assertEquals(0, list.count());

        // remove from empty list
        assertThrows(IllegalArgumentException.class, () -> list.removeFromList("Game"));
        assertEquals(0, list.count());
    }
}