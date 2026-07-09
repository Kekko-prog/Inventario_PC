-- TRIGGER 1: impedisce che la quantità in magazzino scenda sotto zero diventando negativa
CREATE OR REPLACE FUNCTION controlla_quantita_non_negativa()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.quantita < 0 THEN
        RAISE EXCEPTION 'Operazione non permessa: la quantita non puo essere negativa (valore: %)', NEW.quantita;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_quantita_non_negativa
BEFORE INSERT OR UPDATE ON componente
FOR EACH ROW
EXECUTE FUNCTION controlla_quantita_non_negativa();



-- TRIGGER 2: impedisce che il prezzo sia minore o uguale a zero
CREATE OR REPLACE FUNCTION controlla_prezzo_positivo()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.prezzo <= 0 THEN
        RAISE EXCEPTION 'Operazione non permessa: il prezzo deve essere maggiore di zero (valore: %)', NEW.prezzo;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_prezzo_positivo
BEFORE INSERT OR UPDATE ON componente
FOR EACH ROW
EXECUTE FUNCTION controlla_prezzo_positivo();



-- TRIGGER 3: creazione di un log automatico quando viene aggiunto un componente
CREATE OR REPLACE FUNCTION registra_log_nuovo_componente()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO log_componenti (componente_id, azione, descrizione)
    VALUES (NEW.id, 'INSERIMENTO',
            'Aggiunto nuovo componente: ' || NEW.nome || ' (tipo: ' || NEW.tipo || ')');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_log_nuovo_componente
AFTER INSERT ON componente
FOR EACH ROW
EXECUTE FUNCTION registra_log_nuovo_componente();