package io.blockchainetl.bitcoinetl;

import io.blockchainetl.bitcoinetl.utils.ConfigUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Map;


@RunWith(JUnit4.class)
public class ConfigTest {

    @Test
    public void testReadConfig() throws Exception {
        String[] args = ConfigUtils.expandArgs(new String[]{
            "--argsFile=config/test-config.properties",
            "--update",
            "--amountThreshold=20"
        });
        Map<String, String> argsMap = ConfigUtils.parseArgs(args);

        Assert.assertEquals(argsMap.get("--argsFile"), null);
        Assert.assertEquals(argsMap.get("--countThreshold"), "8");
        Assert.assertEquals(argsMap.get("--amountThreshold"), "20");
        Assert.assertTrue(argsMap.containsKey("--update"));
    }

    @Test
    public void testReadConfigWithoutFile() throws Exception {
        String[] args = ConfigUtils.expandArgs(new String[]{
            "--update",
            "--amountThreshold=20"
        });
        Map<String, String> argsMap = ConfigUtils.parseArgs(args);

        Assert.assertEquals(argsMap.get("--argsFile"), null);
        Assert.assertEquals(argsMap.get("--amountThreshold"), "20");
        Assert.assertTrue(argsMap.containsKey("--update"));
    }
}
