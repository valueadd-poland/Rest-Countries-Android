package pl.valueadd.restcountries.utility

import io.dropwizard.testing.junit.MockitoTestRule
import org.junit.Rule
import org.junit.rules.RuleChain
import org.mockito.junit.MockitoJUnit
import toothpick.testing.ToothPickRule

/**
 * Created by Arkadiusz Pałka on 27/08/2018.
 */

abstract class AbstractUnitTest {

    @Rule
    @JvmField
    val toothPickRule = ToothPickRule(this, "Wrap")

    @Rule
    @JvmField
    val testRule = RuleChain.outerRule(toothPickRule).around(MockitoTestRule(this, MockitoJUnit.rule()))
}