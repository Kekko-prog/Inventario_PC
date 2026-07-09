--creazione database e tabelle
DROP TABLE IF EXISTS log_componenti CASCADE;
DROP TABLE IF EXISTS processore CASCADE;
DROP TABLE IF EXISTS scheda_video CASCADE;
DROP TABLE IF EXISTS memoria_ram CASCADE;
DROP TABLE IF EXISTS componente CASCADE;
DROP TABLE IF EXISTS fornitore CASCADE;

-- Tabella Fornitore (entità indipendente)
CREATE TABLE fornitore (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    telefono  VARCHAR(20),
    email     VARCHAR(100)
);

-- Tabella Componente: rappresenta la classe astratta "Componente"
-- (generalizzazione). Il campo "tipo" indica la sottoclasse concreta.
CREATE TABLE componente (
    id            SERIAL PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    prezzo        NUMERIC(10,2) NOT NULL,
    quantita      INTEGER NOT NULL DEFAULT 0,
    tipo          VARCHAR(20) NOT NULL,
    fornitore_id  INTEGER REFERENCES fornitore(id)
);

-- Sottoclasse Processore (specializzazione di Componente)
CREATE TABLE processore (
    componente_id INTEGER PRIMARY KEY REFERENCES componente(id) ON DELETE CASCADE,
    numero_core   INTEGER NOT NULL,
    socket        VARCHAR(30) NOT NULL
);

-- Sottoclasse SchedaVideo (specializzazione di Componente)
CREATE TABLE scheda_video (
    componente_id INTEGER PRIMARY KEY REFERENCES componente(id) ON DELETE CASCADE,
    memoria_vram  INTEGER NOT NULL,
    chipset       VARCHAR(50) NOT NULL
);

-- Sottoclasse MemoriaRam (specializzazione di Componente)
CREATE TABLE memoria_ram (
    componente_id INTEGER PRIMARY KEY REFERENCES componente(id) ON DELETE CASCADE,
    capacita_gb   INTEGER NOT NULL,
    tipo_memoria  VARCHAR(10) NOT NULL
);

-- Tabella di log per la tracciabilità delle operazioni
CREATE TABLE log_componenti (
    id            SERIAL PRIMARY KEY,
    componente_id INTEGER,
    azione        VARCHAR(50),
    data_ora      TIMESTAMP DEFAULT NOW(),
    descrizione   VARCHAR(255)
);




