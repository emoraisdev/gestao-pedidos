package br.com.fiap.mslogistica.model.enums;

public enum EntregaStatus {
    PREPARANDO(0), EM_ANDAMENTO(1), CONCLUIDA(2);

    private int value;

    EntregaStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static EntregaStatus fromValue(int value){
        for (EntregaStatus status : EntregaStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor numérico inválido: " + value);
    }
}
