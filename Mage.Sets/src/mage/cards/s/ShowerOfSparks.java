
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class ShowerOfSparks extends CardImpl {

    public ShowerOfSparks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Shower of Sparks deals 1 damage to target creature and 1 damage to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1)); 
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DamagePlayersEffect(1, TargetController.ANY));
        //** doing it the way below this causes the ability to trigger twice making it do 2 damage to player and 2 damage to target creature                                    
        //Effect effect = new DamageTargetEffect(1);
        //effect.setTargetPointer(new SecondTargetPointer()); 
        //effect.setText("and 1 damage to target player");
        //this.getSpellAbility().addEffect(effect); **//
        //this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        //setText("deals 1 damage to target creature and 1 damage to target player") (not sure where to put this)
    }

    private ShowerOfSparks(final ShowerOfSparks card) {
        super(card);
    }

    @Override
    public ShowerOfSparks copy() {
        return new ShowerOfSparks(this);
    }
}
