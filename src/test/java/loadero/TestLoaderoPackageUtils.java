package loadero;

import loadero.model.LoaderoGroup;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoTestOptions;
import loadero.types.LoaderoModelType;
import loadero.types.LoaderoTestModeType;
import loadero.utils.FunctionBodyParser;
import loadero.utils.LoaderoClientUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestLoaderoPackageUtils {
    
    @Test
    public void testCopyCommonFields() {
        LoaderoTestOptions currentTest = new LoaderoTestOptions();
        currentTest.setId(1);
        currentTest.setName("name");
        currentTest.setMode(LoaderoTestModeType.LOAD);
        currentTest.setScript("script");

        LoaderoTestOptions newTest = new LoaderoTestOptions();
        
        LoaderoTestOptions updatedTest = (LoaderoTestOptions) LoaderoClientUtils.copyUncommonFields(
                currentTest,
                newTest,
                LoaderoModelType.LOADERO_TEST_OPTIONS
        );
        
        // Comparing different fields against each other. Should be the same
        assertAll("Test currentTest with updatedTest. Should be the same.",
                () -> assertEquals(currentTest.getId(), updatedTest.getId()),
                () -> assertEquals(currentTest.getName(), updatedTest.getName()),
                () -> assertEquals(currentTest.getScriptFileId(), updatedTest.getScriptFileId()));
    }
    
    @Test
    public void testApplyNewParamsToScript() {
        Map<String, String> scriptParams = new HashMap<>();
        String appUrl = "https://voice-webapp-3026-dev.twil.io/index.html?identity=";
        
        scriptParams.put("callDuration", "20");
        scriptParams.put("particId", "globalConfig.getParticipant().getId()");
        scriptParams.put("appUrl", appUrl);
        
        String scriptLoc = "src/test/java/loadero/scripts/testui/TestOneOnOneCall.java";
        String scriptWithNewParams = FunctionBodyParser.applyParamsToScript(scriptLoc, scriptParams);
        assertTrue(scriptWithNewParams.contains("callDuration = 20"));
        assertTrue(scriptWithNewParams.contains(appUrl));
    }
    
    @Test
    public void negativeApplyNewParamsToScript() {
        assertThrows(NullPointerException.class, () -> {
            String scriptLoc = "src/test/java/loadero/scripts/testui/TestOneOnOneCall.java";
            String scriptWithNewParams = FunctionBodyParser.applyParamsToScript(scriptLoc, null);
        });
        assertThrows(NullPointerException.class, () -> {
            Map<String, String> scriptParams = new HashMap<>();
            String scriptWithNewParams = FunctionBodyParser.applyParamsToScript(null, scriptParams);
        });
    }
    
    @Test
    public void negativeTestJsonToObject() {
        LoaderoModel model = LoaderoClientUtils.httpEntityToModel(null, null);
        assertNull(model);
    }
    
    @Test
    public void testModelToJson() {
        LoaderoGroup group = new LoaderoGroup();
        group.setName("name");
        group.setCount(1);
        
        String json = LoaderoClientUtils.modelToJson(group);
        assertTrue(json.contains(group.getName()));
    }
    
    @Test
    public void negativeTestFunctionParser() {
        String str = FunctionBodyParser.getScriptContent("");
        assertNull(str);
    }
}
