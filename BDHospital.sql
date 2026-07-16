SELECT current_database();
DROP SCHEMA IF EXISTS clinica CASCADE;
CREATE SCHEMA clinica;




-- =====================================================
-- TABLA PACIENTE
-- =====================================================

CREATE TABLE clinica.paciente (
    id_paciente SERIAL,
    nombre VARCHAR(60) NOT NULL,
    apellido_paterno VARCHAR(60) NOT NULL,
    apellido_materno VARCHAR(60),
    genero VARCHAR(15) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_paciente
        PRIMARY KEY (id_paciente),

    CONSTRAINT chk_paciente_nombre
        CHECK (LENGTH(TRIM(nombre)) >= 2),

    CONSTRAINT chk_paciente_apellido_paterno
        CHECK (LENGTH(TRIM(apellido_paterno)) >= 2),

    CONSTRAINT chk_paciente_genero
        CHECK (
            genero IN (
                'Femenino',
                'Masculino'
            )
        ),

    CONSTRAINT chk_paciente_fecha_nacimiento
        CHECK (fecha_nacimiento <= CURRENT_DATE)
);

-- =====================================================
-- TABLA DOCTOR
-- =====================================================

CREATE TABLE clinica.doctor (
    id_doctor SERIAL,
    nombre VARCHAR(60) NOT NULL,
    apellido_paterno VARCHAR(60) NOT NULL,
    apellido_materno VARCHAR(60),
    especialidad VARCHAR(100) NOT NULL,
    cedula_profesional VARCHAR(30) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(120),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_doctor
        PRIMARY KEY (id_doctor),

    CONSTRAINT uq_doctor_cedula
        UNIQUE (cedula_profesional),

    CONSTRAINT uq_doctor_correo
        UNIQUE (correo),

    CONSTRAINT chk_doctor_nombre
        CHECK (LENGTH(TRIM(nombre)) >= 2),

    CONSTRAINT chk_doctor_apellido
        CHECK (LENGTH(TRIM(apellido_paterno)) >= 2),

    CONSTRAINT chk_doctor_telefono
        CHECK (
            telefono IS NULL
            OR telefono ~ '^[0-9]{10,15}$'
        )
);

-- =====================================================
-- TABLA PACIENTE_DOCTOR
-- =====================================================

CREATE TABLE clinica.paciente_doctor (
    id_paciente_doctor SERIAL,
    id_paciente INTEGER NOT NULL,
    id_doctor INTEGER NOT NULL,
    fecha_asignacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT pk_paciente_doctor
        PRIMARY KEY (id_paciente_doctor),

    CONSTRAINT uq_paciente_doctor
        UNIQUE (id_paciente, id_doctor),

    CONSTRAINT fk_paciente_doctor_paciente
        FOREIGN KEY (id_paciente)
        REFERENCES clinica.paciente(id_paciente)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_paciente_doctor_doctor
        FOREIGN KEY (id_doctor)
        REFERENCES clinica.doctor(id_doctor)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- =====================================================
-- TABLA INGRESO
-- =====================================================

CREATE TABLE clinica.ingreso (
    id_ingreso SERIAL,
    id_paciente INTEGER NOT NULL,
    peso NUMERIC(6,2) NOT NULL,
    fecha_ingreso DATE NOT NULL,
    hora_ingreso TIME NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'INGRESADO',
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_ingreso
        PRIMARY KEY (id_ingreso),

    CONSTRAINT fk_ingreso_paciente
        FOREIGN KEY (id_paciente)
        REFERENCES clinica.paciente(id_paciente)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT chk_ingreso_peso
        CHECK (peso > 0 AND peso <= 500),

    CONSTRAINT chk_ingreso_estado
        CHECK (
            estado IN (
                'INGRESADO',
                'ALTA',
                'HOSPITALIZADO',
                'EGRESADO'
            )
        )
);

-- =====================================================
-- TABLA REGISTRO
-- =====================================================

CREATE TABLE clinica.registro (
    id_registro SERIAL,
    id_ingreso INTEGER NOT NULL,
    id_paciente INTEGER NOT NULL,
    id_doctor INTEGER NOT NULL,
    alergias TEXT,
    observaciones TEXT,
    diagnostico TEXT NOT NULL,
    salida VARCHAR(20) NOT NULL,
    fecha_registro DATE NOT NULL DEFAULT CURRENT_DATE,
    hora_registro TIME NOT NULL DEFAULT CURRENT_TIME,

    CONSTRAINT pk_registro
        PRIMARY KEY (id_registro),

    CONSTRAINT uq_registro_ingreso
        UNIQUE (id_ingreso),

    CONSTRAINT fk_registro_ingreso
        FOREIGN KEY (id_ingreso)
        REFERENCES clinica.ingreso(id_ingreso)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_registro_paciente
        FOREIGN KEY (id_paciente)
        REFERENCES clinica.paciente(id_paciente)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_registro_doctor
        FOREIGN KEY (id_doctor)
        REFERENCES clinica.doctor(id_doctor)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT chk_registro_salida
        CHECK (
            salida IN (
                'ALTA',
                'HOSPITALIZACION'
            )
        ),

    CONSTRAINT chk_registro_diagnostico
        CHECK (
            LENGTH(TRIM(diagnostico)) >= 3
        )
);

-- =====================================================
-- TABLA EGRESO
-- =====================================================

CREATE TABLE clinica.egreso (
    id_egreso SERIAL,
    id_ingreso INTEGER NOT NULL,
    fecha_egreso DATE NOT NULL,
    hora_egreso TIME NOT NULL,
    observaciones TEXT,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_egreso
        PRIMARY KEY (id_egreso),

    CONSTRAINT uq_egreso_ingreso
        UNIQUE (id_ingreso),

    CONSTRAINT fk_egreso_ingreso
        FOREIGN KEY (id_ingreso)
        REFERENCES clinica.ingreso(id_ingreso)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- =====================================================
-- Estado después del registro clínico
-- =====================================================

CREATE OR REPLACE FUNCTION clinica.actualizar_estado_registro()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    IF NEW.salida = 'ALTA' THEN

        UPDATE clinica.ingreso
        SET estado = 'ALTA'
        WHERE id_ingreso = NEW.id_ingreso;

    ELSIF NEW.salida = 'HOSPITALIZACION' THEN

        UPDATE clinica.ingreso
        SET estado = 'HOSPITALIZADO'
        WHERE id_ingreso = NEW.id_ingreso;

    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_actualizar_estado_registro
AFTER INSERT OR UPDATE OF salida
ON clinica.registro
FOR EACH ROW
EXECUTE FUNCTION clinica.actualizar_estado_registro();


-- =====================================================
-- Estado después del egreso
-- =====================================================

CREATE OR REPLACE FUNCTION clinica.actualizar_estado_egreso()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE clinica.ingreso
    SET estado = 'EGRESADO'
    WHERE id_ingreso = NEW.id_ingreso;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_actualizar_estado_egreso
AFTER INSERT
ON clinica.egreso
FOR EACH ROW
EXECUTE FUNCTION clinica.actualizar_estado_egreso();


-- =====================================================
--Validar fecha y hora del egreso
-- =====================================================
CREATE OR REPLACE FUNCTION clinica.validar_fecha_egreso()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_fecha_ingreso DATE;
    v_hora_ingreso TIME;
BEGIN
    SELECT
        fecha_ingreso,
        hora_ingreso
    INTO
        v_fecha_ingreso,
        v_hora_ingreso
    FROM clinica.ingreso
    WHERE id_ingreso = NEW.id_ingreso;

    IF NOT FOUND THEN
        RAISE EXCEPTION
            'El ingreso indicado no existe';
    END IF;

    IF NEW.fecha_egreso < v_fecha_ingreso THEN
        RAISE EXCEPTION
            'La fecha de egreso no puede ser anterior al ingreso';
    END IF;

    IF NEW.fecha_egreso = v_fecha_ingreso
       AND NEW.hora_egreso < v_hora_ingreso THEN

        RAISE EXCEPTION
            'La hora de egreso no puede ser anterior al ingreso';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_validar_fecha_egreso
BEFORE INSERT OR UPDATE
ON clinica.egreso
FOR EACH ROW
EXECUTE FUNCTION clinica.validar_fecha_egreso();


-- =====================================================
--Índices
-- =====================================================
CREATE INDEX idx_paciente_nombre
ON clinica.paciente (
    apellido_paterno,
    apellido_materno,
    nombre
);

CREATE INDEX idx_doctor_nombre
ON clinica.doctor (
    apellido_paterno,
    apellido_materno,
    nombre
);

CREATE INDEX idx_ingreso_paciente
ON clinica.ingreso (id_paciente);

CREATE INDEX idx_ingreso_estado
ON clinica.ingreso (estado);

CREATE INDEX idx_registro_paciente
ON clinica.registro (id_paciente);

CREATE INDEX idx_registro_doctor
ON clinica.registro (id_doctor);

CREATE INDEX idx_egreso_fecha
ON clinica.egreso (fecha_egreso);


-- =====================================================
--Vista para la pestaña Lista de pacientes
-- =====================================================
CREATE OR REPLACE VIEW clinica.vista_pacientes AS
SELECT
    i.id_ingreso,
    p.id_paciente,

    CONCAT_WS(
        ' ',
        p.nombre,
        p.apellido_paterno,
        p.apellido_materno
    ) AS paciente,

    p.genero,
    p.fecha_nacimiento,

    EXTRACT(
        YEAR FROM AGE(
            CURRENT_DATE,
            p.fecha_nacimiento
        )
    )::INTEGER AS edad,

    i.peso,
    i.fecha_ingreso,
    i.hora_ingreso,
    i.estado,

    d.id_doctor,

    CONCAT_WS(
        ' ',
        d.nombre,
        d.apellido_paterno,
        d.apellido_materno
    ) AS doctor,

    d.especialidad,
    r.alergias,
    r.observaciones AS observaciones_registro,
    r.diagnostico,
    r.salida,
    e.fecha_egreso,
    e.hora_egreso,
    e.observaciones AS observaciones_egreso

FROM clinica.ingreso i

INNER JOIN clinica.paciente p
    ON p.id_paciente = i.id_paciente

LEFT JOIN clinica.registro r
    ON r.id_ingreso = i.id_ingreso

LEFT JOIN clinica.doctor d
    ON d.id_doctor = r.id_doctor

LEFT JOIN clinica.egreso e
    ON e.id_ingreso = i.id_ingreso;

	
-- =====================================================
--Doctores de prueba
-- =====================================================


INSERT INTO clinica.doctor (
    nombre,
    apellido_paterno,
    apellido_materno,
    especialidad,
    cedula_profesional,
    telefono,
    correo
)
VALUES
(
    'María',
    'Hernández',
    'López',
    'Medicina general',
    'CED-10001',
    '9511234567',
    'maria.hernandez@hospital.com'
),
(
    'Carlos',
    'Martínez',
    'Ruiz',
    'Medicina interna',
    'CED-10002',
    '9512345678',
    'carlos.martinez@hospital.com'
),
(
    'Ana',
    'Sánchez',
    'García',
    'Pediatría',
    'CED-10003',
    '9513456789',
    'ana.sanchez@hospital.com'
);


