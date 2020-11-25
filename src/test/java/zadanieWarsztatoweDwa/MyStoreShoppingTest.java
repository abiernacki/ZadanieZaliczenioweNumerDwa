package zadanieWarsztatoweDwa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/Cucumber/Features/zadanie-warsztatowe-dwa.feature",
        plugin = {"pretty","html:out"}
)
public class MyStoreShoppingTest {
}
