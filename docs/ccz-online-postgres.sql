CREATE TABLE IF NOT EXISTS "ORIENTACOES" (
	"id" serial NOT NULL UNIQUE,
	"descricao" varchar(255) NOT NULL UNIQUE,
	"titulo" varchar(25) NOT NULL,
	"especie_id" bigint NOT NULL,
	"tipo_solicitacao_id" bigint NOT NULL,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "ALERTA_CIDADAO" (
	"id" serial NOT NULL UNIQUE,
	"endereco" varchar(255),
	"municipio_id" bigint NOT NULL,
	"descricao" varchar(255) NOT NULL UNIQUE,
	"data" timestamp without time zone NOT NULL,
	"coord_latitude" numeric(10,0),
	"coord_longitude" numeric(10,0),
	"tipo_notificacao_id" bigint NOT NULL,
	"especie_id" bigint,
	"usuario_id" bigint NOT NULL,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "ESPECIE" (
	"id" serial NOT NULL UNIQUE,
	"descricao" varchar(255) NOT NULL UNIQUE,
	"imagem" varchar(255),
	"zoonose_id" bigint,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "TIPO_NOTIFICACAO" (
	"id" serial NOT NULL UNIQUE,
	"descricao" varchar(255) NOT NULL UNIQUE,
	"urgencia" bigint NOT NULL,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "USUARIO" (
	"id" serial NOT NULL UNIQUE,
	"nome" bigint NOT NULL,
	"cartao_sus" bigint NOT NULL,
	"perfil" varchar(255) NOT NULL DEFAULT '25',
	"cpf" varchar(11) NOT NULL,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "ALERTA_CCZ" (
	"id" serial NOT NULL UNIQUE,
	"titulo" varchar(25) NOT NULL,
	"descricao" varchar(255) NOT NULL UNIQUE,
	"endereco" varchar(255),
	"municipio_id" bigint NOT NULL,
	"data" timestamp without time zone NOT NULL,
	"coord_latitude" numeric(10,0) NOT NULL,
	"coord_longitude" numeric(10,0) NOT NULL,
	"tipo_notificacao_id" bigint NOT NULL,
	"especie_id" bigint,
	"usuario_id" bigint NOT NULL,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "visita" (
	"id" serial NOT NULL UNIQUE,
	"morador" varchar(255) NOT NULL,
	"data" timestamp without time zone NOT NULL,
	"agente_id" bigint NOT NULL,
	"especie_id" bigint,
	"endereco" varchar(255),
	"municipio_id" bigint NOT NULL,
	"coord_latitude" numeric(10,0),
	"coord_longitude" numeric(10,0),
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "MUNICIPIO" (
	"id" serial NOT NULL UNIQUE,
	"descricao" varchar(255) NOT NULL,
	"uf" varchar(255) NOT NULL,
	"codigo_ibge" bigint,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "ZOONOSE" (
	"id" serial NOT NULL UNIQUE,
	"descricao" varchar(255) NOT NULL,
	PRIMARY KEY ("id")
);

ALTER TABLE "ORIENTACOES" ADD CONSTRAINT "ORIENTACOES_fk3" FOREIGN KEY ("especie_id") REFERENCES "ESPECIE"("id");

ALTER TABLE "ORIENTACOES" ADD CONSTRAINT "ORIENTACOES_fk4" FOREIGN KEY ("tipo_solicitacao_id") REFERENCES "TIPO_NOTIFICACAO"("id");
ALTER TABLE "ALERTA_CIDADAO" ADD CONSTRAINT "ALERTA_CIDADAO_fk2" FOREIGN KEY ("municipio_id") REFERENCES "MUNICIPIO"("id");

ALTER TABLE "ALERTA_CIDADAO" ADD CONSTRAINT "ALERTA_CIDADAO_fk7" FOREIGN KEY ("tipo_notificacao_id") REFERENCES "TIPO_NOTIFICACAO"("id");

ALTER TABLE "ALERTA_CIDADAO" ADD CONSTRAINT "ALERTA_CIDADAO_fk8" FOREIGN KEY ("especie_id") REFERENCES "ESPECIE"("id");

ALTER TABLE "ALERTA_CIDADAO" ADD CONSTRAINT "ALERTA_CIDADAO_fk9" FOREIGN KEY ("usuario_id") REFERENCES "USUARIO"("id");
ALTER TABLE "ESPECIE" ADD CONSTRAINT "ESPECIE_fk3" FOREIGN KEY ("zoonose_id") REFERENCES "ZOONOSE"("id");


ALTER TABLE "ALERTA_CCZ" ADD CONSTRAINT "ALERTA_CCZ_fk4" FOREIGN KEY ("municipio_id") REFERENCES "MUNICIPIO"("id");

ALTER TABLE "ALERTA_CCZ" ADD CONSTRAINT "ALERTA_CCZ_fk8" FOREIGN KEY ("tipo_notificacao_id") REFERENCES "TIPO_NOTIFICACAO"("id");

ALTER TABLE "ALERTA_CCZ" ADD CONSTRAINT "ALERTA_CCZ_fk9" FOREIGN KEY ("especie_id") REFERENCES "ESPECIE"("id");

ALTER TABLE "ALERTA_CCZ" ADD CONSTRAINT "ALERTA_CCZ_fk10" FOREIGN KEY ("usuario_id") REFERENCES "USUARIO"("id");
ALTER TABLE "visita" ADD CONSTRAINT "visita_fk3" FOREIGN KEY ("agente_id") REFERENCES "USUARIO"("id");

ALTER TABLE "visita" ADD CONSTRAINT "visita_fk4" FOREIGN KEY ("especie_id") REFERENCES "ESPECIE"("id");

ALTER TABLE "visita" ADD CONSTRAINT "visita_fk6" FOREIGN KEY ("municipio_id") REFERENCES "MUNICIPIO"("id");

