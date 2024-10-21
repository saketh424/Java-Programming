import java.util.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return symbol + ": $" + price;
    }
}

class Portfolio {
    private Map<Stock, Integer> holdings;
    private double cashBalance;

    public Portfolio(double initialCash) {
        holdings = new HashMap<>();
        cashBalance = initialCash;
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost > cashBalance) {
            System.out.println("Insufficient funds to buy " + quantity + " shares of " + stock.getSymbol());
        } else {
            holdings.put(stock, holdings.getOrDefault(stock, 0) + quantity);
            cashBalance -= cost;
            System.out.println("Bought " + quantity + " shares of " + stock.getSymbol());
        }
    }

    public void sellStock(Stock stock, int quantity) {
        if (!holdings.containsKey(stock) || holdings.get(stock) < quantity) {
            System.out.println("Not enough shares to sell " + quantity + " shares of " + stock.getSymbol());
        } else {
            holdings.put(stock, holdings.get(stock) - quantity);
            cashBalance += stock.getPrice() * quantity;
            System.out.println("Sold " + quantity + " shares of " + stock.getSymbol());
        }
    }

    public void viewPortfolio() {
        System.out.println("Current Portfolio:");
        for (Map.Entry<Stock, Integer> entry : holdings.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " shares");
        }
        System.out.println("Cash balance: $" + cashBalance);
    }

    public double getPortfolioValue() {
        double totalValue = cashBalance;
        for (Map.Entry<Stock, Integer> entry : holdings.entrySet()) {
            totalValue += entry.getKey().getPrice() * entry.getValue();
        }
        return totalValue;
    }
}

public class StockTradingPlatform {
    private static Map<String, Stock> marketData;
    private static Portfolio portfolio;

    public static void main(String[] args) {
        initializeMarketData();
        portfolio = new Portfolio(10000.0); // Start with $10,000 cash

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nStock Trading Platform Menu:");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMarketData();
                    break;
                case 2:
                    buyStock(scanner);
                    break;
                case 3:
                    sellStock(scanner);
                    break;
                case 4:
                    portfolio.viewPortfolio();
                    System.out.println("Total portfolio value: $" + portfolio.getPortfolioValue());
                    break;
                case 5:
                    System.out.println("Exiting the platform. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void initializeMarketData() {
        marketData = new HashMap<>();
        marketData.put("AAPL", new Stock("AAPL", 150.0));
        marketData.put("GOOGL", new Stock("GOOGL", 2800.0));
        marketData.put("AMZN", new Stock("AMZN", 3400.0));
        marketData.put("TSLA", new Stock("TSLA", 700.0));
        marketData.put("MSFT", new Stock("MSFT", 300.0));
    }

    private static void viewMarketData() {
        System.out.println("Current Market Data:");
        for (Stock stock : marketData.values()) {
            System.out.println(stock);
        }
    }

    private static void buyStock(Scanner scanner) {
        System.out.print("Enter the stock symbol to buy: ");
        String symbol = scanner.next().toUpperCase();
        if (marketData.containsKey(symbol)) {
            Stock stock = marketData.get(symbol);
            System.out.print("Enter the quantity to buy: ");
            int quantity = scanner.nextInt();
            portfolio.buyStock(stock, quantity);
        } else {
            System.out.println("Invalid stock symbol.");
        }
    }

    private static void sellStock(Scanner scanner) {
        System.out.print("Enter the stock symbol to sell: ");
        String symbol = scanner.next().toUpperCase();
        if (marketData.containsKey(symbol)) {
            Stock stock = marketData.get(symbol);
            System.out.print("Enter the quantity to sell: ");
            int quantity = scanner.nextInt();
            portfolio.sellStock(stock, quantity);
        } else {
            System.out.println("Invalid stock symbol.");
        }
    }
}
