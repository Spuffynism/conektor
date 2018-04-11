package xyz.ndlr.model.provider.random;

import xyz.ndlr.model.dispatching.mapping.ActionMapping;
import xyz.ndlr.model.dispatching.mapping.ProviderMapping;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;
import xyz.ndlr.model.provider.random.card.Card;
import xyz.ndlr.model.provider.random.card.Suit;
import xyz.ndlr.model.provider.random.card.Value;

import java.util.Random;

@ProviderMapping(value = "random", humanName = "Random")
public class RandomService {
    private static Random random;

    public RandomService() {
        random = new Random();
    }

    @ActionMapping(ActionMapping.DEFAULT_ACTION)
    public ProviderResponse doAction(User user, PipelinedMessage pipelinedMessage) {
        String action = pipelinedMessage.getTextWithoutFirstWord();

        String result;
        if (action.matches("^(roll).*(dice|die)$")) {
            result = String.format("You rolled a dice and got... %d!", getRandomDieRoll());
        } else if (action.matches("^(pick|choose).*(card)$")) {
            result = String.format("You picked a random card and got... %s!",
                    getRandomCard().toHuman());
        } else {
            result = "I don't understand what you asked for.";
        }

        return new ProviderResponse(user, result);
    }

    private Card getRandomCard() {
        return new Card(getRandomEnumValue(Suit.class), getRandomEnumValue(Value.class));
    }

    /**
     * Maybe move this to an EnumUtil class?
     *
     * @param clazz an enum class
     * @param <T>   type of the enum
     * @return a random value from the enum class
     */
    private static <T extends Enum<?>> T getRandomEnumValue(Class<T> clazz) {
        return clazz.getEnumConstants()[random.nextInt(clazz.getEnumConstants().length)];
    }

    /**
     * Gets a random valid die roll value.
     *
     * @return a random valid die roll value
     */
    private int getRandomDieRoll() {
        return getRandomNumberInRange(1, 6);
    }

    /**
     * Gets a random value between min and max.
     *
     * @param min lower inclusive bound
     * @param max higher inclusive bound
     * @return a random value between min and max
     */
    private int getRandomNumberInRange(int min, int max) {
        if (max < min)
            throw new IllegalArgumentException("invalid bounds");

        return random.nextInt((max - min) + 1) + min;
    }
}
