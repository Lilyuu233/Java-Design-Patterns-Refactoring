package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import javafx.scene.input.KeyCode;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private Animal animal;

    @BeforeEach
    void setUp() {
        animal = mock(Animal.class);
        doNothing().when(animal).setImage(any());
        when(animal.getX()).thenReturn(300.0);
        when(animal.getY()).thenReturn(733 + 26.6667);
    }

    @Test
    void testInitialPosition() {
        assertEquals(300, animal.getX());
        assertEquals(733 + 26.6667, animal.getY(), 0.001);
        assertFalse(animal.isDead());
    }

    @Test
    void testResetPosition() {
        animal.setX(400);
        animal.setY(500);
        animal.setCarDeath(true);
        animal.resetPosition();
        assertEquals(300, animal.getX());
        assertEquals(733 + 26.6667, animal.getY(), 0.001); // DEFAULT_Y
        assertFalse(animal.isDead());
    }

    @Test
    void testMovementX() {
        when(animal.getMovementX(KeyCode.A)).thenReturn(-21.3333);
        when(animal.getMovementX(KeyCode.D)).thenReturn(21.3333);
        when(animal.getMovementX(KeyCode.W)).thenReturn(0.0);

        assertEquals(-21.3333, animal.getMovementX(KeyCode.A), 0.001);
        assertEquals(21.3333, animal.getMovementX(KeyCode.D), 0.001);
        assertEquals(0, animal.getMovementX(KeyCode.W), 0.001);
    }

    @Test
    void testMovementY() {
        when(animal.getMovementY(KeyCode.W)).thenReturn(-26.6667);
        when(animal.getMovementY(KeyCode.S)).thenReturn(26.6667);
        when(animal.getMovementY(KeyCode.A)).thenReturn(0.0);

        assertEquals(-26.6667, animal.getMovementY(KeyCode.W), 0.001);
        assertEquals(26.6667, animal.getMovementY(KeyCode.S), 0.001);
        assertEquals(0, animal.getMovementY(KeyCode.A), 0.001);
    }

    @Test
    void testSetAndClearCurrentPlatform() {
        assertFalse(animal.isOnPlatform());
        animal.setCurrentPlatform(new Actor(0, 0, 0) {
            @Override
            public void act(long now) {
            }
        });
        assertFalse(animal.isOnPlatform());
        animal.clearCurrentPlatform();
        assertFalse(animal.isOnPlatform());
    }

    @Test
    void testIsInWaterWithoutPlatform() {
        animal.setY(450);
        assertFalse(animal.isInWaterWithoutPlatform());
    }

}
