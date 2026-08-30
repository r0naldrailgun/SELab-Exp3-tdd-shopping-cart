import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartDecimalPrecisionTest {

    @Test
    void totalOfDecimalPricesShouldEqualExpectedMonetaryTotal() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Item A", 0.1);
        cart.addItem("Item B", 0.2);

        assertEquals(0.30, cart.getTotal());
    }
}
