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
    private double fatorDeRepeti��o;

    /***************************************************************************
     * Fator de Repeti��o indica o qu�o o cart�o ir� se repetir futuramente.    *
     * Um fator de 0 indica que o cart�o � novo e DEVE aparecer na revis�o      *
     * Um cart�o que tem erros frequentes tem um fator pequeno(mas nunca zero)  *
     * Um cart�o que tem acertos frequentes tem um fator maior, portanto, tem   *
     * menos frequencia nas revis�es.                                           *
     ****************************************************************************/
    public FlashCard(String pergunta, String resposta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.tag = null;
        fatorDeRepeti��o = 0.0;
    }

    public FlashCard(String pergunta, String resposta, String tag) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.tag = tag;
        fatorDeRepeti��o = 0.0;
    }

    public double getFatorDeRepeti��o() {
        return fatorDeRepeti��o;
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

    public void setFatorDeRepeti��o(double fatorDeRepeti��o) {
        this.fatorDeRepeti��o = fatorDeRepeti��o;
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
        if (this.fatorDeRepeti��o > o.fatorDeRepeti��o) {
            return 1;
        } else if (this.fatorDeRepeti��o < o.fatorDeRepeti��o) {
            return -1;
        }else
            return 0;
    }
}