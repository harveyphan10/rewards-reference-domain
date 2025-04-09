package rewards;

import common.money.MonetaryAmount;
import config.RewardsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dungptm
 */
public class RewardNetworkTests {

    private RewardNetwork rewardNetwork;

    @BeforeEach
    public void setUp() {
        // Create the test configuration for the application:

        ApplicationContext context = SpringApplication.run(TestInfrastructureConfig.class);

        // Get the bean to use to invoke the application
        rewardNetwork = context.getBean(RewardNetwork.class);
    }

    @Test
    public void testRewardForDining1() {
        Dining dining = Dining.createDining("100.00", "1234123412341234", "1234567890");
        RewardConfirmation rewardConfirmation = rewardNetwork.rewardAccountFor(dining);

        assertNotNull(rewardConfirmation);
        assertNotNull(rewardConfirmation.getConfirmationNumber());

        AccountContribution contribution = rewardConfirmation.getAccountContribution();
        assertNotNull(contribution);

        assertEquals("123456789", contribution.getAccountNumber());

        assertEquals(MonetaryAmount.valueOf("8.00"), contribution.getAmount());

        assertEquals(2, contribution.getDistributions().size());

        assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Annabelle").getAmount());
        assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Corgan").getAmount());
    }
}
