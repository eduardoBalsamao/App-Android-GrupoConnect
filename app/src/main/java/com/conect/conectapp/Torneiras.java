package com.conect.conectapp;

public class Torneiras {

    public String code, dono, status, nome;

    Torneiras(){

    }



    public Torneiras(String code, String dono, String status, String nome) {
        this.code=code;
        this.dono=dono;
        this.status=status;
        this.nome=nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
