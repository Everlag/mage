/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class AsylumVisitor extends CardImpl {
    
    public AsylumVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add("Vampire");
        this.subtype.add("Wizard");
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // At the beginning of each player's upkeep, if that player has no cards in hand, you draw a card and you lose 1 life.
        this.addAbility(new AsylumVisitorTriggeredAbility());

        // Madness {1}{B}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{1}{B}")));
    }

    public AsylumVisitor(final AsylumVisitor card) {
        super(card);
    }

    @Override
    public AsylumVisitor copy() {
        return new AsylumVisitor(this);
    }
}

class AsylumVisitorTriggeredAbility extends TriggeredAbilityImpl {

    public AsylumVisitorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AsylumVisitorEffect());
    }

    public AsylumVisitorTriggeredAbility(final AsylumVisitorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AsylumVisitorTriggeredAbility copy() {
        return new AsylumVisitorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player upkeepPlayer = game.getPlayer(event.getPlayerId());
        if (upkeepPlayer != null && upkeepPlayer.getHand().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each player's upkeep, if that player has no cards in hand, you draw a card and you lose 1 life";
    }
}

class AsylumVisitorEffect extends OneShotEffect {

    public AsylumVisitorEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you draw a card and you lose 1 life";
    }

    public AsylumVisitorEffect(final AsylumVisitorEffect effect) {
        super(effect);
    }

    @Override
    public AsylumVisitorEffect copy() {
        return new AsylumVisitorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player upkeepPlayer = game.getPlayer(game.getActivePlayerId());
        if (you != null && upkeepPlayer != null && upkeepPlayer.getHand().isEmpty()) {
            you.drawCards(1, game);
            you.loseLife(1, game, false);
            return true;
        }
        return false;
    }
}