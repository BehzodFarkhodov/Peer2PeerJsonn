package uz.pdp.repository;

import uz.pdp.model.Card;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardRepo extends BaseRepo<Card> {

    private static final CardRepo cardRepo = new CardRepo();

    public static CardRepo getInstance() {
        return cardRepo;
    }

    public CardRepo() {
        super.path = "src/main/resources/cards.json";
        super.type = Card.class;
    }

    public List<Card> getAllCard(UUID userId) {
        ArrayList<Card> cards = getAll();
        return cards.stream().filter(card -> card.getOwnerId().equals(userId)).toList();
    }





}
