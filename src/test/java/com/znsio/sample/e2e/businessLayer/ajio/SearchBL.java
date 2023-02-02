package com.znsio.sample.e2e.businessLayer.ajio;

import com.context.TestExecutionContext;
import com.znsio.e2e.entities.Platform;
import com.znsio.e2e.runner.Runner;
import com.znsio.sample.e2e.entities.SAMPLE_TEST_CONTEXT;
import com.znsio.sample.e2e.screen.ajio.CartScreen;
import com.znsio.sample.e2e.screen.ajio.HomeScreen;
import com.znsio.sample.e2e.screen.ajio.ProductScreen;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchBL {
    private static final Logger LOGGER = Logger.getLogger(SearchBL.class.getName());
    private final TestExecutionContext context;
    private final SoftAssertions softly;
    private final String currentUserPersona;
    private final Platform currentPlatform;

    public SearchBL(String userPersona, Platform forPlatform) {
        long threadId = Thread.currentThread()
                .getId();
        this.context = Runner.getTestExecutionContext(threadId);
        softly = Runner.getSoftAssertion(threadId);
        this.currentUserPersona = userPersona;
        this.currentPlatform = forPlatform;
        Runner.setCurrentDriverForUser(userPersona, forPlatform, context);
    }

    public SearchBL() {
        long threadId = Thread.currentThread()
                .getId();
        this.context = Runner.getTestExecutionContext(threadId);
        softly = Runner.getSoftAssertion(threadId);
        this.currentUserPersona = SAMPLE_TEST_CONTEXT.ME;
        this.currentPlatform = Runner.platform;
    }

    public SearchBL searchProduct(Map searchData) {
        LOGGER.info("searchProduct" + searchData);
        assertThat(HomeScreen.get().searchByImage(searchData).numberOfProductFound()).as("Number of results found for product").isGreaterThan(0);
        return this;
    }

    public SearchBL prepareCart() {
        ProductScreen productScreen = ProductScreen.get();
        context.addTestState("productName", productScreen.getProductName());
        LOGGER.info("productName: " + context.getTestState("productName"));
        productScreen.addProductToCart();
        return this;
    }

    public SearchBL verifyCart() {
        String actualProductName = CartScreen.get().getActualProductName();
        LOGGER.info("Actual product name in the cart"+actualProductName);
        assertThat(actualProductName).as("Product in the Cart").isEqualTo(context.getTestState("productName"));
        return this;
    }


}
