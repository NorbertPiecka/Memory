public class TurnThread extends Thread {
    private Card card1, card2;

    public TurnThread(Card card1, Card card2){
        this.card1 = card1;
        this.card2 = card2;
    }

    public void run(){
        if (card1.getId() == card2.getId()) { //Znaleziono pare
            card1.setEnabled(false); //Usunięcie mozliwosci klikniecia kart ponownie
            card2.setEnabled(false);
        } else {
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            card1.turnDown(); //Odworcenie kart
            card2.turnDown();
        }
    }
}
