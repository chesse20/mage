package mage.cards.t;

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
public final class ToughDoggy extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.DOG, "Dogs");

    public ToughDoggy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect( //buff other creatures of same type by +0/+1
                0, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private ToughDoggy(final ToughDoggy card) {
        super(card);
    }

    @Override
    public ToughDoggy copy() {
        return new ToughDoggy(this);
    }
}