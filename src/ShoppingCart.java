import exceptions.InvalidAmountException;
import exceptions.InvalidCardNumberException;
import exceptions.SystemNotWorkingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
        System.out.println(product.getName() + " sepete eklendi.");
    }

    public void removeProduct(Product product) {
        products.remove(product);
        System.out.println(product.getName() + " sepetten çıkarıldı.");
    }
    public List<Product> getProducts() {
        return products;
    }
    public void pay() throws SystemNotWorkingException {
        System.out.println("Ödeme yapılıyor...");


        int random = (int) (Math.random() * 100);
        System.out.println("Sistem kontrol ediliyor...");

        if (random > 75) {
            throw new SystemNotWorkingException("Sistem şu anda çalışmıyor. Lütfen daha sonra tekrar deneyin.");
        }

        System.out.println("Ödeme tamamlandı.");
    }

    public void printInvoice() {
        double total = 0.0;
        System.out.println("Sepetinizdeki Ürünler:");
        for (Product product : products) {
            System.out.println("- " + product.getName() + ": " + product.getPrice() + " TL");
            total += product.getPrice();
        }
        System.out.println("Toplam Tutar: " + total + " TL");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Kart Numarası: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Son Kullanma Tarihi (MM/YY): ");
        String expiryDate = scanner.nextLine();
        System.out.println("Güvenlik Kodu: ");
        int securityCode = scanner.nextInt();

        try {
            validatePaymentAmount(total);
            validateCardNumber(cardNumber);
            validateExpiryDate(expiryDate);
            validateSecurityCode(securityCode);

            pay();
        } catch (InvalidAmountException | InvalidCardNumberException | IllegalArgumentException |
                 SystemNotWorkingException e) {
            System.out.println("Ödeme işlemi sırasında bir hata oluştu: " + e.getMessage());
            System.out.println("İşlemi tekrar deneyin.");
        }
    }

    private void validatePaymentAmount(double amount) throws InvalidAmountException {
        if (amount <= 0 || Math.floor(amount) != amount) {
            throw new InvalidAmountException("Geçersiz ödeme tutarı.");
        }
    }

    private void validateCardNumber(String cardNumber) throws InvalidCardNumberException {
        if (!cardNumber.matches("\\d{16}")) {
            throw new InvalidCardNumberException("Geçersiz kart numarası.");
        }
    }

    private void validateExpiryDate(String expiryDate) {
        String[] parts = expiryDate.split("/");
        if (parts.length != 2 || parts[0].length() != 2 || parts[1].length() != 2) {
            throw new IllegalArgumentException("Geçersiz son kullanma tarihi formatı.");
        }
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);
        if (month < 1 || month > 12 || year < 21) {
            throw new IllegalArgumentException("Geçersiz son kullanma tarihi.");
        }
    }

    private void validateSecurityCode(int securityCode) {
        String securityCodeStr = String.valueOf(securityCode);
        if (securityCodeStr.length() != 3 || !securityCodeStr.matches("\\d{3}")) {
            throw new IllegalArgumentException("Geçersiz güvenlik kodu.");
        }
    }
}
