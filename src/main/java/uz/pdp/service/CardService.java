package uz.pdp.service;

import uz.pdp.model.Card;
import uz.pdp.repository.CardRepo;

import java.util.List;
import java.util.UUID;

public class CardService extends BaseService<Card, CardRepo> {


    public CardService(CardRepo repository) {
        super(repository);
    }

    @Override
    public boolean check(Card card) {
        return false;
    }
    public List<Card> getAllCard(UUID userId) {
        return repository.getAllCard(userId);
    }
}
