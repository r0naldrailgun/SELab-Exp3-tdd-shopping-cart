import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartDiscountBoundaryTest {

    @Test
    void cartAtExactlyOneHundredDoesNotReceiveDiscount() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Item A", 40.0);
        cart.addItem("Item B", 60.0);

        assertEquals(100.0, cart.getTotalWithDiscount());
    }
}
