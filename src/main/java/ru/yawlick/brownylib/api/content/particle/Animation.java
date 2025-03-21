package ru.yawlick.brownylib.api.content.particle;

import ru.yawlick.brownylib.api.IFastBrowny;
import ru.yawlick.brownylib.common.content.particle.AbstractAnimation;

public interface Animation extends IFastBrowny {
    <T extends AbstractAnimation> T start();

    <T extends AbstractAnimation> T stop();
}
