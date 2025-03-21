package ru.yawlick.brownylib.api.content.particle;

import ru.yawlick.brownylib.api.IFastBrowny;
import ru.yawlick.brownylib.common.content.particle.AbstractScenario;

public interface Scenario extends IFastBrowny {
    <T extends AbstractScenario> T start();

    <T extends AbstractScenario> T stop();
}
