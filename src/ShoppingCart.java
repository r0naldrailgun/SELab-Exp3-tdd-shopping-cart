import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private static final BigDecimal MINIMUM_CHECKOUT_TOTAL = new BigDecimal("50.00");
    private static final BigDecimal MAXIMUM_CHECKOUT_TOTAL = new BigDecimal("10000.00");

    private Map<String, Double> items = new HashMap<>();

    public void addItem(String name, double price) {
        items.put(name, price);
    }

    public boolean removeItem(String name) {
        if (items.containsKey(name)) {
            items.remove(name);
            return true;
        }
        return false;
    }

    public double getTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (double price : items.values()) {
            total = total.add(BigDecimal.valueOf(price));
        }

        return total.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    public double getTotalWithDiscount() {
        BigDecimal total = BigDecimal.valueOf(getTotal());

        if (total.compareTo(BigDecimal.valueOf(100.0)) >= 0) {
            return total
                    .multiply(new BigDecimal("0.9"))
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        return total.doubleValue();
    }

    public int getItemCount() {
        return items.size();
    }

    public void validateCheckout() {
        BigDecimal total = BigDecimal.valueOf(getTotal());

        if (total.compareTo(MINIMUM_CHECKOUT_TOTAL) < 0
                || total.compareTo(MAXIMUM_CHECKOUT_TOTAL) > 0) {
            throw new IllegalStateException("Cart total must be between 50.00 and 10000.00.");
        }
    }

    public void updateItemPrice(String itemName, double newPrice) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name must not be null or blank.");
        }

        if (!Double.isFinite(newPrice) || newPrice <= 0) {
            throw new IllegalArgumentException("Item price must be finite and greater than zero.");
        }

        if (!items.containsKey(itemName)) {
            return;
        }

        items.put(itemName, newPrice);
    }
}
