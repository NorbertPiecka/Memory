public class SyncGameControler extends Thread {
    private GameWindow main;
    private Bot bot;
    private volatile int turn;
    private TurnThread turnThread;

    public SyncGameControler(GameWindow main, Bot bot, int turn) {
        this.main = main;
        this.bot = bot;
        this.turn = turn;
    }

    public synchronized void controlGame(Card card1, Card card2, Object o) {
        if(o instanceof GameWindow){
            turnThread = new TurnThread(card1,card2);
            turnThread.start();
            while(turn == 2){
                try{
                    wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            if(card1.getId() == card2.getId()) {
                main.updateMatches();
                bot.updateEnable(card1.getId());
                bot.removeCard(card1);
                bot.removeCard(card2);
                main.updatePlayerScore();
                main.changeTurn(2);
                main.setEnable(1);
            }else{
                main.changeTurn(turn);
                bot.addCard(card1);
                bot.addCard(card2);
                main.setEnable(3);
                this.turn = 2;
                notifyAll();
            }
        }
        //BOT ||||||||||||
        if(o instanceof Bot){

            Card temp1 = card1, temp2 = card2;
            while(turn == 1){
                try{
                    wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            main.setWhoTurn();
            main.setEnable(3);
            try{
                Thread.sleep(1500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            card1.turnUp();
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            card2.turnUp();

            turnThread = new TurnThread(card1,card2);
            turnThread.start();

            if(card1.getId() == card2.getId()) {
                main.updateMatches();
                bot.updateEnable(card1.getId()); 
                bot.removeCard(card1);
                bot.removeCard(card2);
                main.updateOponentScore();
                main.changeTurn(1);
                main.setEnable(3);
            }else{
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                main.changeTurn(turn);
                bot.addCard(card1);
                bot.addCard(card2);
                main.setEnable(1);
                this.turn = 1;
                notifyAll();
            }
        }
    }
}

