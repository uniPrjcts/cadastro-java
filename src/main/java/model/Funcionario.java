package model;

import java.util.Objects;

public class Funcionario {
    private String id;
    private String nome;
    private Departamento departamento;
    private Cargo cargo;
    
    public Funcionario() {
    }
    
    public Funcionario(String id, String nome, Departamento departamento, Cargo cargo) {
        this.id = id;
        this.nome = nome;
        this.departamento = departamento;
        this.cargo = cargo;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Departamento getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    
    public Cargo getCargo() {
        return cargo;
    }
    
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    @Override
    public String toString() {
        return this.nome + " - " + this.cargo.getNome() + " (" + this.departamento.getNome() + ")";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Funcionario other = (Funcionario) obj;
        return Objects.equals(this.id, other.id);
    }
}