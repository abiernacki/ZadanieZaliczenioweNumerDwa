package zadanieWarsztatoweDwa;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MyStoreShopping {

    private WebDriver driver;

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
        searchField.sendKeys("Hummingbird Printed Sweater");
        searchField.submit();

        WebElement selectedProduct = driver.findElement(By.xpath("//*[@id=\"js-product-list\"]//article//*[@class='product-description']//a[text()[contains(.,'Hummingbird printed sweater')]]"));
        selectedProduct.click();
    }

    @And("wybieramy rozmiar i liczbe sztuk, dodajemy produkt do koszyka przechodzimy do proceed to checkout")
    public void wybieramyRozmiarILiczbeSztukDodajemyProduktDoKoszykaPrzechodzimyDoProceedToCheckout() {

        WebElement size = driver.findElement(By.id("group_1"));
        size.sendKeys("L");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement quantity = driver.findElement(By.id("quantity_wanted"));
        quantity.clear();
        quantity.sendKeys("5");

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

        TakesScreenshot scrShot = ((TakesScreenshot)driver);
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("src/main/resources/screenshot/foto.png");
        FileUtils.copyFile(srcFile, destFile);
    }
}
