package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801, JayDi85
 */
public class ExploreSourceEffect extends OneShotEffect {

    private final DynamicValue amount;

    private static final String REMINDER_TEXT = ". <i>(Reveal the top card of your library. "
            + "Put that card into your hand if it's a land. Otherwise, put a +1/+1 counter on "
            + "this creature, then put the card back or put it into your graveyard.)</i>";

    public ExploreSourceEffect() {
        this(true, "it");
    }

    public ExploreSourceEffect(boolean showAbilityHint, String explorerText) {
        this(1, showAbilityHint, explorerText);
    }

    public ExploreSourceEffect(int amount, boolean showAbilityHint, String explorerText) {
        this(StaticValue.get(amount), showAbilityHint, explorerText);
    }

    public ExploreSourceEffect(DynamicValue amount, boolean showAbilityHint, String explorerText) {
        super(Outcome.Benefit);

        this.amount = amount;
        // triggered ability text gen will replace {this} with "it" where applicable
        staticText = explorerText + " explores" + (showAbilityHint ? REMINDER_TEXT : "");
    }

    protected ExploreSourceEffect(final ExploreSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public ExploreSourceEffect copy() {
        return new ExploreSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return explorePermanent(game, source.getSourceId(), source, this.amount.calculate(game, source, this));
    }

    public static boolean explorePermanent(Game game, UUID permanentId, Ability source, int amount) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
        if (controller == null || permanent == null || amount <= 0) {
            return false;
        }

        for (int i = 0; i < amount; ++i) {
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.EXPLORED, permanentId, source, permanent.getControllerId()));
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.revealCards("Explored card", new CardsImpl(card), game);
                if (card.isLand(game)) {
                    controller.moveCards(card, Zone.HAND, source, game);
                } else {
                    addCounter(game, permanent, source);
                    if (controller.chooseUse(Outcome.Neutral, "Put " + card.getLogName() + " in your graveyard?", source, game)) {
                        controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    } else {
                        game.informPlayers(controller.getLogName() + " leaves " + card.getLogName() + " on top of their library.");
                    }
                }
            } else {
                // If no card is revealed, most likely because that player's library is empty,
                // the exploring creature receives a +1/+1 counter.
                addCounter(game, permanent, source);
            }
        }
        return true;
    }

    private static void addCounter(Game game, Permanent permanent, Ability source) {
        if (game.getState().getZone(permanent.getId()) == Zone.BATTLEFIELD) { // needed in case LKI object is used
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
    }

}
