package lambdasinaction.chap11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BestPriceFinder {

    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
                                                   new Shop("LetsSaveBig"),
                                                   new Shop("MyFavoriteShop"),
                                                   new Shop("BuyItAll"),
                                                   new Shop("ShopEasy"));

    private final Executor executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });
    
    public List<String> findPrices(String product) {
        return shops.parallelStream().map(shop -> {
            System.out.println(Thread.currentThread() + shop.getName());
            return shop.getPrice(product);
        }).collect(Collectors.toList());
    }
    
    public List<String> findPricesFuture(String product) {
//      return shops.stream()
//              .map(shop -> {
//                  System.out.println(Thread.currentThread()+shop.getName());
//                  return CompletableFuture.supplyAsync(() -> shop.getPrice(product));
//              })
//              .map(f -> {
//                  String s = f.join();
//                  System.out.println(Thread.currentThread()+s);
//                  return s;
//              })
//              .collect(Collectors.toList());
      
      
//      List<CompletableFuture<String>> priceFutures = findPricesStream(product)
//              .collect(Collectors.<CompletableFuture<String>>toList());
      
//      List<CompletableFuture<String>> priceFutures =
//              shops.stream()
//              .map(shop -> {
//                  System.out.println(Thread.currentThread()+shop.getName());
//                  return CompletableFuture.supplyAsync(() -> shop.getPrice(product));
//              })
//              .collect(Collectors.toList());
      
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .collect(Collectors.toList());
      
        return priceFutures.stream().map(f -> {
            String s = f.join();
            System.out.println(Thread.currentThread() + s);
            return s;
        }).collect(Collectors.toList());
  }

    public List<String> findPricesSequential(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(
                        quote -> CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote), executor)));
    }
    
    public void printPricesStream(String product) {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesStream(product)
                .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }
    
    public Stream<CompletableFuture<String>> findExchangePricesStream(String product) {
        return shops
                .stream().map(
                        shop -> CompletableFuture.supplyAsync(() -> shop.getDblPrice(product), executor)
                                .thenCombine(CompletableFuture.supplyAsync(() -> ExchangeService
                                        .getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD), executor),
                                        (price, rate) -> (price * rate) + ""));
    }
    
    public void findExchangePricesStream2(String product) {
        // 동작 안함..
//        CompletableFuture[] futures = 
//                shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getDblPrice(product), executor)
//                .thenCombine(CompletableFuture.supplyAsync(() -> ExchangeService
//                        .getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD), executor),
//                        (price, rate) -> (price * rate) + ""))
//                .map(f -> f.thenAccept(System.out::println)).toArray(size-> new CompletableFuture[size]);
        
        // ok
//        Stream<CompletableFuture<String>> stream = shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getDblPrice(product), executor)
//                .thenCombine(CompletableFuture.supplyAsync(() -> ExchangeService
//                        .getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD), executor),
//                        (price, rate) -> (price * rate) + ""));
//        
//        CompletableFuture[] futures = stream.map(f -> f.thenAccept(System.out::println))
//            .toArray(size -> new CompletableFuture[size]);
//        
//        CompletableFuture.allOf(futures).join();
        
        Stream<CompletableFuture<String>> stream = shops
                .stream().map(
                        shop -> CompletableFuture.supplyAsync(() -> shop.getDblPrice(product), executor)
                                .thenCombine(CompletableFuture.supplyAsync(() -> ExchangeService
                                        .getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD), executor),
                                        (price, rate) -> (price * rate) + ""));

        CompletableFuture[] futures = stream.map(f -> f.thenAccept(System.out::println))
                .toArray(size -> new CompletableFuture[size]);

        CompletableFuture.allOf(futures).join();
    }
    
    public void printExchangePricesStream(String product) {
        long start = System.nanoTime();
        CompletableFuture[] futures = findExchangePricesStream(product)
                .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
//        CompletableFuture.anyOf(futures).join();
//        System.out.println("Some shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }
}
