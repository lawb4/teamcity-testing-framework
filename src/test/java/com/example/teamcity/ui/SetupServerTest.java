package com.example.teamcity.ui;

import com.example.teamcity.ui.setup.FirstStartPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SetupServerTest extends BaseUiTest {
    @Test
    @Tag("Setup")
    public void setupTeamCityServerTest() {
        FirstStartPage.open().setupFirstStart();
    }
}
