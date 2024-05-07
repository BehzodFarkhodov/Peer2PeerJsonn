package uz.pdp.repository;

import uz.pdp.model.Card;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
                .toList();
    }

    public List<Card> getAllCards() {
        return getAll();
    }

    public boolean deleteCard(String cardNumber) {
        List<Card> cards = getAll();
        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                card.setActive(false);
                write(new ArrayList<>(cards));
                return true;
            }
        }
        return false;
    }
    public Card getById(UUID id) {
        ArrayList<Card> allCards = getAll();
        for (Card card : allCards) {
            if (card.getId().equals(id)) {
                return card;
            }
        }
        return null;
    }
    public void update(Card card) {
        List<Card> cards = getAll();
        List<Card> updatedCards = cards.stream()
                .map(c -> c.getId().equals(card.getId()) ? card : c)
                .collect(Collectors.toList());
        write((ArrayList<Card>) updatedCards);
    }








}
