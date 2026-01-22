package br.dev.as.ccz.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "TIPO_NOTIFICACAO")
public class TipoNotificacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, unique = true, length = 255)
    private String descricao;

    @Column(name = "urgencia", nullable = false)
    private Long urgencia;
}