package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author ChesseTheWasp
 */
public final class DangerLizard extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.LIZARD, "Lizards");

    public DangerLizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect( //buff other creatures of same type by +1/+0
                1, 0, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private DangerLizard(final DangerLizard card) {
        super(card);
    }

    @Override
    public DangerLizard copy() {
        return new DangerLizard(this);
    }
}