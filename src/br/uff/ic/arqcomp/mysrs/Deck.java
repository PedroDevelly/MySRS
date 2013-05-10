package br.uff.ic.arqcomp.mysrs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Deck implements Serializable {

    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private ArrayList<FlashCard> deck;
    private int limite; //armazena numero máximo de cartões especificado pelo usuário.

    public Deck(int limite) {
        deck = new ArrayList<FlashCard>();
        this.limite = limite;
    }

    public String getPergunta(int i){
        return deck.get(i).getPergunta();
    }
    
    public String getResposta(int i){
        return deck.get(i).getResposta();
    }
    
    public void setFatorRepeticao(int i, double fator){
        deck.get(i).setFatorDeRepetição(fator*deck.get(i).getFatorDeRepetição());
    }
    
    public void setNewFatorRepeticao(int i, double fator){
        deck.get(i).setFatorDeRepetição(fator);
    }
    
    public double getFatorRepeticao(int i){
        return deck.get(i).getFatorDeRepetição();
    }
    
    public boolean addFlashCard(String pergunta, String resposta, String tag) {
        return deck.add(new FlashCard(pergunta, resposta, tag));
    }
    public boolean removeFlashCard(FlashCard fc){
        return deck.remove(fc);
    }
    public boolean editFlashCardPergunta(FlashCard fc, String pergunta){
        
        if(deck.contains(fc)){
            fc.setPergunta(pergunta);
            return true;
        }else return false;
        
    }
    
    public int getLimite(){
        return this.limite;
    }
    public void setLimite(int limite){
        this.limite = limite;
    }
    
    public void sort(){
        Collections.sort(deck);
    }
    
    public ArrayList getAllCards(){
        
        return deck;
//        ArrayList<FlashCard> al = new ArrayList<FlashCard>();
//        al.addAll(deck);
//        return al;
    }

    
}
