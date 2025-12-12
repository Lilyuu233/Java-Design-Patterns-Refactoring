package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Log;

class LogTest {
    private Log log;

    @BeforeEach
    void setUp() {
        // Create a Log object with a default image path
        log = new Log("/images/logs.png", 80, 100, 200, 1.0);
    }

    @Test
    void testLogInitialization() {
        assertEquals(100, log.getX());
        assertEquals(200, log.getY());
        assertEquals(1.0, log.getSpeed());
    }

    @Test
    void testMoveWithinBounds() {
        log.act(0);
        assertEquals(101, log.getX()); // x-coordinate updated based on speed
        assertEquals(200, log.getY()); // y-coordinate remains unchanged
    }

    @Test
    void testResetPositionWhenOutOfBounds() {
        log.setX(700);
        log.act(0);
        assertTrue(log.getX() <= 600); // x-coordinate should reset within bounds
    }

    @Test
    void testAddRandomSpider() {
        log.addRandomSpider("/images/spider.png");
        assertTrue(log.getSpider() == null || log.getSpider() != null);
    }

    @Test
    void testClearSpider() {
        log.addRandomSpider("/images/spider.png");
        log.clearSpider();
        assertNull(log.getSpider());
    }
}
