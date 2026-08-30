import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartDiscountPrecisionTest {

    @Test
    void discountedTotalIsRoundedToTwoDecimalPlaces() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Item A", 100.01);

        assertEquals(90.01, cart.getTotalWithDiscount());
    }
}
