package uz.pdp.service;

import uz.pdp.model.Card;
import uz.pdp.model.Transaction;
import uz.pdp.repository.CardRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static uz.pdp.repository.CardRepo.cardRepo;

public class CardService extends BaseService<Card, CardRepo> {


    private static final CardService cardService = new CardService();
    private final CommissionService commissionService = new CommissionService();


    public static CardService getInstance() {
        return cardService;
    }

    public CardService() {
        super(CardRepo.getInstance());
    }


    @Override
    public boolean check(Card card) {
        ArrayList<Card> cards = getAll();
        for (Card card1 : cards) {
            if (card1.getCardNumber().equals(card.getCardNumber())) {
                return true;
            }
        }
        return false;
    }

    public List<Card> getAllCard(UUID userId) {
        return repository.getAllCard(userId);
    }
    public List<Card> getAllCards(UUID userId) {
        return repository.getAllCards();
    }
    public void delete(UUID id) {
       repository.delete(id);
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
    public boolean transferMoney(UUID fromCard, UUID toCard, Double price){
      Optional<Card>  byId = findById(fromCard);
      Optional<Card> byCard = findById(toCard);
      Double byCards = commissionService.getByCard(byId.get().getCategory(),byCard.get().getCategory());
      double result = price * byCards / 100;
      if(byId.get().getBalance() <= price ){
          return false;
      }
      repository.transferMoney(fromCard,toCard,price,result);
      return true;
    }
    public List<Card> getAllCards(String card){
        return repository.getAllCards(card);
    }
}
