package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String telefone;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Quarto> quartos = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Hotel() {}

    public Hotel(Long id, String nome, String endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public List<Quarto> getQuartos() { return quartos; }
    public void setQuartos(List<Quarto> quartos) { this.quartos = quartos; }

    public List<Funcionario> getFuncionarios() { return funcionarios; }
    public void setFuncionarios(List<Funcionario> funcionarios) { this.funcionarios = funcionarios; }
}