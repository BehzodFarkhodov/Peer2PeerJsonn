package uz.pdp.service;

import uz.pdp.model.Card;
import uz.pdp.repository.CardRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.repository.CardRepo.cardRepo;

public class CardService extends BaseService<Card, CardRepo> {


    public CardService(CardRepo repository) {
        super(repository);
    }

    @Override
    public boolean check(Card card) {
        ArrayList<Card> cards = getAll();
        for (Card card1 : cards) {
            if(card1.getCardNumber().equals(card.getCardNumber())){
                return true;
            }
        }
        return false;
    }
    public List<Card> getAllCard(UUID userId) {
        return repository.getAllCard(userId);
    }
    public List<Card> getAllCards() {
        return repository.getAllCards();
    }
    public boolean deleteCard(String cardNumber) {
        return repository.deleteCard(cardNumber);
    }
    public boolean delete(UUID id) {
        return repository.delete(id);
    }
    public Card getCardByCardNumber(String cardNumber) {
        List<Card> allCards = cardRepo.getAll();
        for (Card card : allCards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        return null;
    }
}
