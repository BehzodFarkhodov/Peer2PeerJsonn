package uz.pdp.repository;

import uz.pdp.exception.DataNotFoundException;
import uz.pdp.model.Card;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CardRepo extends BaseRepo<Card> {

   public static final CardRepo cardRepo = new CardRepo();

    public static CardRepo getInstance() {
        return cardRepo;
    }

    public CardRepo() {
        super.path = "src/main/resources/cards.json";
        super.type = Card.class;
    }

    public List<Card> getAllCard(UUID userId) {
        ArrayList<Card> cards = getAll();
        return cards.stream()
                .filter(card -> card.getOwnerId().equals(userId) && card.isActive())
                .collect(Collectors.toList());
    }

    public List<Card> getAllCards() {
        return getAll();
    }


    public Card getById(UUID id) throws DataNotFoundException {

        return getAll().stream()
                .filter((card -> card.getId().equals(id)))
                .findAny()
                .orElseThrow(new Supplier<DataNotFoundException>() {
                    @Override
                    public DataNotFoundException get() {
                        return new DataNotFoundException("data not found");
                    }
                });

    }


public void update(Card card) {
    List<Card> cards = getAll();
    List<Card> updatedCards = cards.stream()
            .map(c -> c.getId().equals(card.getId()) ? card : c)
            .collect(Collectors.toList());
    write((ArrayList<Card>) updatedCards);
}








}
