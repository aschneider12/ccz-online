package br.dev.as.ccz.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORIENTACOES")
public class OrientacoesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, unique = true, length = 255)
    private String descricao;

    @Column(name = "titulo", nullable = false, length = 25)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "especie_id", nullable = false)
    private EspecieEntity especie;

    @ManyToOne
    @JoinColumn(name = "tipo_solicitacao_id", nullable = false)
    private TipoNotificacaoEntity tipoSolicitacao;
}