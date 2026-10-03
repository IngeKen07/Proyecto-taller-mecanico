
CREATE TABLE cliente (
    id_cliente  VARCHAR2(20)  NOT NULL,
    nombre      VARCHAR2(100) NOT NULL,
    telefono    VARCHAR2(20),
    direccion   VARCHAR2(200),
    CONSTRAINT pk_cliente PRIMARY KEY (id_cliente)
);

CREATE TABLE vehiculo (
    placa       VARCHAR2(15)  NOT NULL,
    marca       VARCHAR2(50),
    modelo      VARCHAR2(50),
    id_cliente  VARCHAR2(20)  NOT NULL,
    CONSTRAINT pk_vehiculo PRIMARY KEY (placa),
    CONSTRAINT fk_vehiculo_cliente FOREIGN KEY (id_cliente)
        REFERENCES cliente (id_cliente)
);

CREATE OR REPLACE PROCEDURE sp_registrar_cliente (
    p_id        IN VARCHAR2,
    p_nombre    IN VARCHAR2,
    p_telefono  IN VARCHAR2,
    p_direccion IN VARCHAR2
) AS
    v_existe NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_existe FROM cliente WHERE id_cliente = p_id;
    IF v_existe > 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'El identificador del cliente ya existe.');
    END IF;

    INSERT INTO cliente VALUES (p_id, p_nombre, p_telefono, p_direccion);
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE sp_modificar_cliente (
    p_id        IN VARCHAR2,
    p_nombre    IN VARCHAR2,
    p_telefono  IN VARCHAR2,
    p_direccion IN VARCHAR2
) AS
BEGIN
    UPDATE cliente
       SET nombre = p_nombre, telefono = p_telefono, direccion = p_direccion
     WHERE id_cliente = p_id;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'El cliente no existe.');
    END IF;
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE sp_eliminar_cliente (p_id IN VARCHAR2) AS
    v_vehiculos NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_vehiculos FROM vehiculo WHERE id_cliente = p_id;

    IF v_vehiculos > 0 THEN
        RAISE_APPLICATION_ERROR(-20003,
            'No se puede eliminar el cliente porque tiene vehículos asociados.');
    END IF;

    DELETE FROM cliente WHERE id_cliente = p_id;
    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'El cliente no existe.');
    END IF;
    COMMIT;
END;
/