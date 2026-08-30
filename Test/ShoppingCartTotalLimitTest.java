import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShoppingCartTotalLimitTest {

    @Test
    void acceptsCartAtMinimumAllowedTotal() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);

        assertDoesNotThrow(cart::validateCheckout);
    }

    @Test
    void acceptsCartAtMaximumAllowedTotal() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Laptop", 10_000.0);

        assertDoesNotThrow(cart::validateCheckout);
    }

    @Test
    void rejectsCartBelowMinimumWithoutChangingItsState() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Pen", 49.99);

        assertThrows(IllegalStateException.class, cart::validateCheckout);

        assertEquals(1, cart.getItemCount());
        assertEquals(49.99, cart.getTotal());
    }

    @Test
    void rejectsCartAboveMaximumWithoutChangingItsState() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Premium Laptop", 10_000.01);

        assertThrows(IllegalStateException.class, cart::validateCheckout);

        assertEquals(1, cart.getItemCount());
        assertEquals(10_000.01, cart.getTotal());
    }
}
