package zadanieWarsztatoweDwa;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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
    }

    @And("potwierdzenie adresu, wybranie metody obior")
    public void potwierdzenieAdresuWybranieMetodyObior() {
    }

    @And("wybranie metody platności, klikniecie order with obligation to pay")
    public void wybranieMetodyPlatnościKlikniecieOrderWithObligationToPay() {
    }

    @Then("screenshot z potwierdzeniem zamówienia i kwota")
    public void screenshotZPotwierdzeniemZamówieniaIKwota() {
    }
}
