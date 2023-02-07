package com.znsio.sample.e2e.steps;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.znsio.e2e.runner.Runner;
import com.znsio.e2e.tools.Drivers;
import com.znsio.sample.e2e.businessLayer.ajio.SearchBL;
import com.znsio.sample.e2e.entities.SAMPLE_TEST_CONTEXT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;

public class AjioSteps {
    private static final Logger LOGGER = Logger.getLogger(AjioSteps.class.getName());
    private final TestExecutionContext context;
    private final Drivers allDrivers;

    public AjioSteps() {
        context = SessionContext.getTestExecutionContext(Thread.currentThread()
                .getId());
        LOGGER.info("context: " + context.getTestName());
        allDrivers = (Drivers) context.getTestState(SAMPLE_TEST_CONTEXT.ALL_DRIVERS);
        LOGGER.info("allDrivers: " + (null == allDrivers));
    }

    @Given("I search for products using {string}")
    public void iSearchForProductsUsing(String searchtype) {
        LOGGER.info(System.out.printf("iSearchForProductsUsing:'%s' - Persona:'%s'", searchtype, SAMPLE_TEST_CONTEXT.ME));
        allDrivers.createDriverFor(SAMPLE_TEST_CONTEXT.ME, Runner.platform, context);
        new SearchBL().searchProduct(Runner.getTestDataAsMap(searchtype));
    }

    @When("I add the product to the cart")
    public void iAddTheProductToTheCart() {
        LOGGER.info(System.out.printf("iAddTheProductToTheCart:- Persona:'%s'", SAMPLE_TEST_CONTEXT.ME));
        new SearchBL().prepareCart();
    }

    @Then("I should see the product in the cart")
    public void iShouldSeeTheProductInTheCart() {
        LOGGER.info(System.out.printf("iShouldSeeTheProductInTheCart:- Persona:'%s'", SAMPLE_TEST_CONTEXT.ME));
        new SearchBL().verifyCart();
    }
}