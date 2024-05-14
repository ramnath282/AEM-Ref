package com.mattel.ecomm.core.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class UsableNetScriptModelTest {

    @InjectMocks
    UsableNetScriptModel usableNetScriptModel;

    @Test
    public void testSetterGetters() throws IllegalArgumentException, IllegalAccessException {
        usableNetScriptModel.setUsableNetScriptPath("usableNetScriptPath");
        usableNetScriptModel.setAccessibilitySwitch(true);
        Assert.assertEquals("usableNetScriptPath", usableNetScriptModel.getUsableNetScriptPath());
        Assert.assertTrue(usableNetScriptModel.isAccessibilitySwitch());
    }

}
