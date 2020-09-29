package org.mage.test.cards.single.c19;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChainerNightmareAdeptTest extends CardTestPlayerBase {

    private static final String chainer = "Chainer, Nightmare Adept";
    private static final String mountain = "Mountain";
    private static final String maaka = "Feral Maaka";
    private static final String khenra = "Defiant Khenra";
    private static final String rings = "Rings of Brighthearth";

    @Test
    public void testChainer() {
        addCard(Zone.BATTLEFIELD, playerA, chainer);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 4);
        addCard(Zone.HAND, playerA, mountain, 2);
        addCard(Zone.GRAVEYARD, playerA, maaka, 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, maaka);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, maaka);

        attack(1, playerA, maaka);

        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerA, maaka, 1);
        assertGraveyardCount(playerA, maaka, 1);
        assertAbility(playerA, maaka, HasteAbility.getInstance(), true);
    }

    @Test
    public void testChainerTwice() {
        addCard(Zone.BATTLEFIELD, playerA, chainer);
        addCard(Zone.BATTLEFIELD, playerA, rings);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 6);
        addCard(Zone.HAND, playerA, mountain);
        addCard(Zone.GRAVEYARD, playerA, maaka);
        addCard(Zone.GRAVEYARD, playerA, khenra);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, maaka);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, khenra);

        attack(1, playerA, maaka);
        attack(1, playerA, khenra);

        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerA, maaka, 1);
        assertTapped(maaka, true);
        assertAbility(playerA, maaka, HasteAbility.getInstance(), true);
        assertGraveyardCount(playerA, maaka, 0);

        assertPermanentCount(playerA, khenra, 1);
        assertTapped(khenra, true);
        assertAbility(playerA, maaka, HasteAbility.getInstance(), true);
        assertGraveyardCount(playerA, khenra, 0);

        assertLife(playerB, 20 - 2 - 2);
    }
}
