// ©OnPoint 2024
package com.ateam.onpoint;

// For testing classes (run through IntelliJ in JUnit)
/*
import com.ateam.onpoint.gui.OnPointGUI;
import com.ateam.onpoint.gui.Sidebar;
import com.ateam.onpoint.gui.WindowPane;
*/

import org.junit.jupiter.api.Test;

// Include all assertions (throw/catch for JUnit Test
import static org.junit.jupiter.api.Assertions.*;

/**
 * <h3>This class is OnPoint's main Test Code class</h3>
 * <br>
 * Primarily designed to be able to affirm integrity of the created
 * files and applications of our current architecture in order to
 * make sure it functions as intended!
 */
class ApplicationEntryTest {

    private final String FAIL_GUI = "ERR - Null Component in GUI";
    private final String FAIL_ENTRY = "ERR - Null Component in GUI";

    /**
     * Basic verification to be used within the test code in verifying a path to class
     */
    void VerifyClass(String path, String failText) {
        try {
            Class.forName(path);
        } catch (ClassNotFoundException e) {
            fail(failText);
        }
    }

    @Test
    void VerifyEntrySuccess() {
        ApplicationEntry testEntry = new ApplicationEntry();
        assertNotEquals("", testEntry.agentStylesheet, FAIL_ENTRY);
        System.out.println("ApplicationEntry.java passed with SUCCESS!");
        return;
    }

    @Test
    public void VerifyGUIComponents() {
        String curPath;
        VerifyClass(curPath = "com.ateam.onpoint.gui.Dashboard", FAIL_GUI + curPath);
        VerifyClass(curPath = "com.ateam.onpoint.gui.OnPointGUI", FAIL_GUI + curPath);
        VerifyClass(curPath = "com.ateam.onpoint.gui.Sidebar", FAIL_GUI + curPath);
        VerifyClass(curPath = "com.ateam.onpoint.gui.WindowPane", FAIL_GUI + curPath);

        // ~/components
        // To be added and asserted
        //VerifyClass(curPath = "com.ateam.onpoint.gui.components.NavTree", FAIL_GUI + curPath);

        // ~/content
        VerifyClass(curPath = "com.ateam.onpoint.gui.content.ContentHeader", FAIL_GUI + curPath);
        VerifyClass(curPath = "com.ateam.onpoint.gui.content.ScheduleView", FAIL_GUI + curPath);
        VerifyClass(curPath = "com.ateam.onpoint.gui.content.TaskView", FAIL_GUI + curPath);
        return;
    }

    @Test
    public void VerifyPlatformFunctionality() {
        String curOS;
        if((curOS = System.getProperty("os.name")) == null)
        {
            fail("Unrecognized OS Platform");
        }
        System.out.println("Running on" + curOS + " successfully!");
        return;
    }
}