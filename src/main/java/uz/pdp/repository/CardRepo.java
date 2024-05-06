package uz.pdp.repository;

import uz.pdp.model.Card;

public class CardRepo extends BaseRepo<Card> {

    private static final CardRepo cardRepo=new CardRepo();

    public static CardRepo getInstance() {
        return cardRepo;
    }

    public CardRepo() {
    }
}
