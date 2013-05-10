package br.uff.ic.arqcomp.mysrs;

import java.io.Serializable;


public class FlashCard implements Comparable<FlashCard>, Serializable {

    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String pergunta;
    private String resposta;
    private String tag;
    private double fatorDeRepetição;

    /***************************************************************************
     * Fator de Repetição indica o quão o cartão irá se repetir futuramente.    *
     * Um fator de 0 indica que o cartão é novo e DEVE aparecer na revisão      *
     * Um cartão que tem erros frequentes tem um fator pequeno(mas nunca zero)  *
     * Um cartão que tem acertos frequentes tem um fator maior, portanto, tem   *
     * menos frequencia nas revisões.                                           *
     ****************************************************************************/
    public FlashCard(String pergunta, String resposta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.tag = null;
        fatorDeRepetição = 0.0;
    }

    public FlashCard(String pergunta, String resposta, String tag) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.tag = tag;
        fatorDeRepetição = 0.0;
    }

    public double getFatorDeRepetição() {
        return fatorDeRepetição;
    }

    public String getPergunta() {
        return pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public String getTag() {
        return tag;
    }

    public void setFatorDeRepetição(double fatorDeRepetição) {
        this.fatorDeRepetição = fatorDeRepetição;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int compareTo(FlashCard o) {
        if (this.fatorDeRepetição > o.fatorDeRepetição) {
            return 1;
        } else if (this.fatorDeRepetição < o.fatorDeRepetição) {
            return -1;
        }else
            return 0;
    }
}