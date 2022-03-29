package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Target;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EachTargetPointer extends NonFixedTargetPointer {

    public EachTargetPointer() {
        super();
    }

    public EachTargetPointer(final EachTargetPointer targetPointer) {
        super(targetPointer);
    }

    @Override
    public EachTargetPointer copy() {
        return new EachTargetPointer(this);
    }

    @Override
    protected List<UUID> getTargetIds(Game game, Ability source) {
        return source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}