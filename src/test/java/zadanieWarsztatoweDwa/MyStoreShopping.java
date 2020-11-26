package zadanieWarsztatoweDwa;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class MyStoreShopping {

    private WebDriver driver;

    private boolean isDiscountPrice;

    @Given("wlaczamy strone sklepu PrestaShop, mamy zarejestrowanego uzytkownika")
    public void userIsLoggedInPrestaShop() {

        System.setProperty("webdriver.chrome.driver",
                "src/main/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("https://prod-kurs.coderslab.pl/");
    }

    @When("logowanie na stworzonego uzytkownika")
    public void logowanieNaStworzonegoUzytkownika() {

        WebElement signIn = driver.findElement(By.xpath("//*[@id=\"_desktop_user_info\"]/div/a"));
        signIn.click();

        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.clear();
        emailInput.sendKeys("jankowalski@o.pl");

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.clear();
        passwordInput.sendKeys("kotek777");

        WebElement signInButton = driver.findElement(By.id("submit-login"));
        signInButton.click();
    }

    @And("wybieramy do zakupu Hummingbird Printed Sweater")
    public void wybieramyDoZakupuHummingbirdPrintedSweater() {

        WebElement searchField = driver.findElement(By.name("s"));
        searchField.sendKeys("Hummingbird printed t-shirt");
        searchField.submit();

        WebElement selectedProduct = driver.findElement(By.xpath("//*[@id=\"js-product-list\"]//article//*[@class='product-description']//a[text()[contains(.,'Hummingbird printed t-shirt')]]"));
        selectedProduct.click();

        try {
            WebElement addressesWindow = driver.findElement(By.cssSelector(".discount.discount-percentage"));
            System.out.println("Koszulka jest w promocji!! Kup taniej o 20%");
            isDiscountPrice = true;
        } catch (NoSuchElementException e) {
            System.out.println("Koszulka nie jest w promocji");
            isDiscountPrice = false;
        }

        WebElement countryInput = driver.findElement(By.xpath("//label/input[@value ='11']"));
        countryInput.click();
    }

    @And("wybieramy (.*) i (.*), dodajemy produkt do koszyka przechodzimy do proceed to checkout")
    public void wybieramyRozmiarILiczbeSztukDodajemyProduktDoKoszykaPrzechodzimyDoProceedToCheckout(String size, String quantity) {

        WebElement sizeSelect = driver.findElement(By.id("group_1"));
        sizeSelect.sendKeys(size);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement quantitySelect = driver.findElement(By.id("quantity_wanted"));
        quantitySelect.clear();
        quantitySelect.sendKeys(quantity);

        WebElement addToCart = driver.findElement(By.cssSelector(".btn.btn-primary.add-to-cart"));
        addToCart.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement proceedToCheckout = driver.findElement(By.cssSelector("#blockcart-modal > div > div > div.modal-body > div > div.col-md-7 > div > div > a"));
        proceedToCheckout.click();

        WebElement proceedToCheckoutSecond = driver.findElement(By.cssSelector("#main > div > div.cart-grid-right.col-xs-12.col-lg-4 > div.card.cart-summary > div.checkout.cart-detailed-actions.card-block > div > a"));
        proceedToCheckoutSecond.click();
    }


    @And("potwierdzenie adresu, wybranie metody obioru")
    public void potwierdzenieAdresuWybranieMetodyObior() {

        WebElement addressConfirm = driver.findElement(By.name("confirm-addresses"));
        addressConfirm.click();

        WebElement paymentConfirm = driver.findElement(By.name("confirmDeliveryOption"));
        paymentConfirm.click();
    }

    @And("wybranie metody platności, klikniecie order with obligation to pay")
    public void wybranieMetodyPlatnościKlikniecieOrderWithObligationToPay() {

        WebElement paymentChoise = driver.findElement(By.id("payment-option-1"));
        paymentChoise.click();

        WebElement birdClic = driver.findElement(By.id("conditions_to_approve[terms-and-conditions]"));
        birdClic.click();

        WebElement buttonAprove = driver.findElement(By.cssSelector(".btn.btn-primary.center-block"));
        buttonAprove.click();
    }

    @Then("screenshot z potwierdzeniem zamówienia i kwota")
    public void screenshotZPotwierdzeniemZamówieniaIKwota() throws IOException {

        Random random= new Random();
        String number = String.valueOf(random.nextInt(10000000));

        TakesScreenshot scrShot = ((TakesScreenshot)driver);
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("src/main/resources/screenshot/foto"+number+".png");
        FileUtils.copyFile(srcFile, destFile);

        WebElement orderSelect = driver.findElement(By.cssSelector("a[title='View my customer account']"));
        orderSelect.click();

        WebElement historySelect = driver.findElement(By.id("history-link"));
        historySelect.click();

        List<WebElement> money = driver.findElements(By.xpath("//table/tbody/tr/td"));
        String cash = money.get(1).getText();

        List<WebElement> trElements = driver.findElements(By.tagName("tr"));
        String lastOrder = trElements.get(1).getText();
        Assert.assertTrue(lastOrder.contains("Awaiting check payment"));
        Assert.assertTrue(lastOrder.contains(cash));

        Assert.assertTrue(isDiscountPrice);

    }
}
