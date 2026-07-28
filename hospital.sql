--
-- PostgreSQL database dump
--

\restrict X1OfqT5BWi3GbRNpFtmzpLeYHpDJn70je3uEy1oBdy2SAB21ZKb55Kv6iXYh00z

-- Dumped from database version 17.10
-- Dumped by pg_dump version 17.10

-- Started on 2026-07-27 23:05:31

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 16523)
-- Name: clinica; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA clinica;


ALTER SCHEMA clinica OWNER TO postgres;

--
-- TOC entry 236 (class 1255 OID 16639)
-- Name: actualizar_estado_egreso(); Type: FUNCTION; Schema: clinica; Owner: postgres
--

CREATE FUNCTION clinica.actualizar_estado_egreso() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE clinica.ingreso
    SET estado = 'EGRESADO'
    WHERE id_ingreso = NEW.id_ingreso;

    RETURN NEW;
END;
$$;


ALTER FUNCTION clinica.actualizar_estado_egreso() OWNER TO postgres;

--
-- TOC entry 235 (class 1255 OID 16637)
-- Name: actualizar_estado_registro(); Type: FUNCTION; Schema: clinica; Owner: postgres
--

CREATE FUNCTION clinica.actualizar_estado_registro() RETURNS trigger
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


ALTER FUNCTION clinica.actualizar_estado_registro() OWNER TO postgres;

--
-- TOC entry 237 (class 1255 OID 16641)
-- Name: validar_fecha_egreso(); Type: FUNCTION; Schema: clinica; Owner: postgres
--

CREATE FUNCTION clinica.validar_fecha_egreso() RETURNS trigger
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


ALTER FUNCTION clinica.validar_fecha_egreso() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 234 (class 1259 OID 16689)
-- Name: detalle_receta; Type: TABLE; Schema: clinica; Owner: postgres
--

CREATE TABLE clinica.detalle_receta (
    id_detalle_receta integer NOT NULL,
    id_receta integer NOT NULL,
    medicamento character varying(150) NOT NULL,
    dosis character varying(100) NOT NULL,
    frecuencia character varying(100) NOT NULL,
    duracion character varying(100),
    via_administracion character varying(50),
    indicaciones text,
    CONSTRAINT chk_dosis CHECK ((length(TRIM(BOTH FROM dosis)) >= 1)),
    CONSTRAINT chk_frecuencia CHECK ((length(TRIM(BOTH FROM frecuencia)) >= 2)),
    CONSTRAINT chk_medicamento CHECK ((length(TRIM(BOTH FROM medicamento)) >= 2))
);


ALTER TABLE clinica.detalle_receta OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16688)
-- Name: detalle_receta_id_detalle_receta_seq; Type: SEQUENCE; Schema: clinica; Owner: postgres
--

CREATE SEQUENCE clinica.detalle_receta_id_detalle_receta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE clinica.detalle_receta_id_detalle_receta_seq OWNER TO postgres;

--
-- TOC entry 5034 (class 0 OID 0)
-- Dependencies: 233
-- Name: detalle_receta_id_detalle_receta_seq; Type: SEQUENCE OWNED BY; Schema: clinica; Owner: postgres
--

ALTER SEQUENCE clinica.detalle_receta_id_detalle_receta_seq OWNED BY clinica.detalle_receta.id_detalle_receta;


--
-- TOC entry 221 (class 1259 OID 16538)
-- Name: doctor; Type: TABLE; Schema: clinica; Owner: postgres
--

CREATE TABLE clinica.doctor (
    id_doctor integer NOT NULL,
    nombre character varying(60) NOT NULL,
    apellido_paterno character varying(60) NOT NULL,
    apellido_materno character varying(60),
    especialidad character varying(100) NOT NULL,
    cedula_profesional character varying(30) NOT NULL,
    telefono character varying(15),
    correo character varying(120),
    activo boolean DEFAULT true NOT NULL,
    fecha_registro timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_doctor_apellido CHECK ((length(TRIM(BOTH FROM apellido_paterno)) >= 2)),
    CONSTRAINT chk_doctor_nombre CHECK ((length(TRIM(BOTH FROM nombre)) >= 2)),
    CONSTRAINT chk_doctor_telefono CHECK (((telefono IS NULL) OR ((telefono)::text ~ '^[0-9]{10,15}$'::text)))
);


ALTER TABLE clinica.doctor OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16537)
-- Name: doctor_id_doctor_seq; Type: SEQUENCE; Schema: clinica; Owner: postgres
--

CREATE SEQUENCE clinica.doctor_id_doctor_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE clinica.doctor_id_doctor_seq OWNER TO postgres;

--
-- TOC entry 5035 (class 0 OID 0)
-- Dependencies: 220
-- Name: doctor_id_doctor_seq; Type: SEQUENCE OWNED BY; Schema: clinica; Owner: postgres
--

ALTER SEQUENCE clinica.doctor_id_doctor_seq OWNED BY clinica.doctor.id_doctor;


--
-- TOC entry 229 (class 1259 OID 16621)
-- Name: egreso; Type: TABLE; Schema: clinica; Owner: postgres
--

CREATE TABLE clinica.egreso (
    id_egreso integer NOT NULL,
    id_ingreso integer NOT NULL,
    fecha_egreso date NOT NULL,
    hora_egreso time without time zone NOT NULL,
    observaciones text,
    fecha_creacion timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE clinica.egreso OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16620)
-- Name: egreso_id_egreso_seq; Type: SEQUENCE; Schema: clinica; Owner: postgres
--

CREATE SEQUENCE clinica.egreso_id_egreso_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE clinica.egreso_id_egreso_seq OWNER TO postgres;

--
-- TOC entry 5036 (class 0 OID 0)
-- Dependencies: 228
-- Name: egreso_id_egreso_seq; Type: SEQUENCE OWNED BY; Schema: clinica; Owner: postgres
--

ALTER SEQUENCE clinica.egreso_id_egreso_seq OWNED BY clinica.egreso.id_egreso;


--
-- TOC entry 225 (class 1259 OID 16575)
-- Name: ingreso; Type: TABLE; Schema: clinica; Owner: postgres
--

CREATE TABLE clinica.ingreso (
    id_ingreso integer NOT NULL,
    id_paciente integer NOT NULL,
    peso numeric(6,2) NOT NULL,
    fecha_ingreso date NOT NULL,
    hora_ingreso time without time zone NOT NULL,
    estado character varying(20) DEFAULT 'INGRESADO'::character varying NOT NULL,
    fecha_creacion timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_ingreso_estado CHECK (((estado)::text = ANY ((ARRAY['INGRESADO'::character varying, 'ALTA'::character varying, 'HOSPITALIZADO'::character varying, 'EGRESADO'::character varying])::text[]))),
    CONSTRAINT chk_ingreso_peso CHECK (((peso > (0)::numeric) AND (peso <= (500)::numeric)))
);


ALTER TABLE clinica.ingreso OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16574)
-- Name: ingreso_id_ingreso_seq; Type: SEQUENCE; Schema: clinica; Owner: postgres
--

CREATE SEQUENCE clinica.ingreso_id_ingreso_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE clinica.ingreso_id_ingreso_seq OWNER TO postgres;

--
-- TOC entry 5037 (class 0 OID 0)
-- Dependencies: 224
-- Name: ingreso_id_ingreso_seq; Type: SEQUENCE OWNED BY; Schema: clinica; Owner: postgres
--

ALTER SEQUENCE clinica.ingreso_id_ingreso_seq OWNED BY clinica.ingreso.id_ingreso;


--
-- TOC entry 219 (class 1259 OID 16525)
-- Name: paciente; Type: TABLE; Schema: clinica; Owner: postgres
--

CREATE TABLE clinica.paciente (
    id_paciente integer NOT NULL,
    nombre character varying(60) NOT NULL,
    apellido_paterno character varying(60) NOT NULL,
    apellido_materno character varying(60),
    genero character varying(15) NOT NULL,
    fecha_nacimiento date NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    fecha_registro timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_paciente_apellido_paterno CHECK ((length(TRIM(BOTH FROM apellido_paterno)) >= 2)),
    CONSTRAINT chk_paciente_fecha_nacimiento CHECK ((fecha_nacimiento <= CURRENT_DATE)),
    CONSTRAINT chk_paciente_genero CHECK (((genero)::text = ANY ((ARRAY['Femenino'::character varying, 'Masculino'::character varying])::text[]))),
    CONSTRAINT chk_paciente_nombre CHECK ((length(TRIM(BOTH FROM nombre)) >= 2))
);


ALTER TABLE clinica.paciente OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16554)
-- Name: paciente_doctor; Type: TABLE; Schema: clinica; Owner: postgres
--

CREATE TABLE clinica.paciente_doctor (
    id_paciente_doctor integer NOT NULL,
    id_paciente integer NOT NULL,
    id_doctor integer NOT NULL,
    fecha_asignacion timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE clinica.paciente_doctor OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16553)
-- Name: paciente_doctor_id_paciente_doctor_seq; Type: SEQUENCE; Schema: clinica; Owner: postgres
--

CREATE SEQUENCE clinica.paciente_doctor_id_paciente_doctor_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE clinica.paciente_doctor_id_paciente_doctor_seq OWNER TO postgres;

--
-- TOC entry 5038 (class 0 OID 0)
-- Dependencies: 222
-- Name: paciente_doctor_id_paciente_doctor_seq; Type: SEQUENCE OWNED BY; Schema: clinica; Owner: postgres
--

ALTER SEQUENCE clinica.paciente_doctor_id_paciente_doctor_seq OWNED BY clinica.paciente_doctor.id_paciente_doctor;


--
-- TOC entry 218 (class 1259 OID 16524)
-- Name: paciente_id_paciente_seq; Type: SEQUENCE; Schema: clinica; Owner: postgres
--

CREATE SEQUENCE clinica.paciente_id_paciente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE clinica.paciente_id_paciente_seq OWNER TO postgres;

--
-- TOC entry 5039 (class 0 OID 0)
-- Dependencies: 218
-- Name: paciente_id_paciente_seq; Type: SEQUENCE OWNED BY; Schema: clinica; Owner: postgres
--

ALTER SEQUENCE clinica.paciente_id_paciente_seq OWNED BY clinica.paciente.id_paciente;


--
-- TOC entry 232 (class 1259 OID 16671)
-- Name: receta; Type: TABLE; Schema: clinica; Owner: postgres
--

CREATE TABLE clinica.receta (
    id_receta integer NOT NULL,
    id_registro integer NOT NULL,
    fecha_receta date DEFAULT CURRENT_DATE NOT NULL,
    indicaciones_generales text,
    fecha_creacion timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE clinica.receta OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16670)
-- Name: receta_id_receta_seq; Type: SEQUENCE; Schema: clinica; Owner: postgres
--

CREATE SEQUENCE clinica.receta_id_receta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE clinica.receta_id_receta_seq OWNER TO postgres;

--
-- TOC entry 5040 (class 0 OID 0)
-- Dependencies: 231
-- Name: receta_id_receta_seq; Type: SEQUENCE OWNED BY; Schema: clinica; Owner: postgres
--

ALTER SEQUENCE clinica.receta_id_receta_seq OWNED BY clinica.receta.id_receta;


--
-- TOC entry 227 (class 1259 OID 16591)
-- Name: registro; Type: TABLE; Schema: clinica; Owner: postgres
--

CREATE TABLE clinica.registro (
    id_registro integer NOT NULL,
    id_ingreso integer NOT NULL,
    id_paciente integer NOT NULL,
    id_doctor integer NOT NULL,
    alergias text,
    observaciones text,
    diagnostico text NOT NULL,
    salida character varying(20) NOT NULL,
    fecha_registro date DEFAULT CURRENT_DATE NOT NULL,
    hora_registro time without time zone DEFAULT CURRENT_TIME NOT NULL,
    CONSTRAINT chk_registro_diagnostico CHECK ((length(TRIM(BOTH FROM diagnostico)) >= 3)),
    CONSTRAINT chk_registro_salida CHECK (((salida)::text = ANY ((ARRAY['ALTA'::character varying, 'HOSPITALIZACION'::character varying])::text[])))
);


ALTER TABLE clinica.registro OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16590)
-- Name: registro_id_registro_seq; Type: SEQUENCE; Schema: clinica; Owner: postgres
--

CREATE SEQUENCE clinica.registro_id_registro_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE clinica.registro_id_registro_seq OWNER TO postgres;

--
-- TOC entry 5041 (class 0 OID 0)
-- Dependencies: 226
-- Name: registro_id_registro_seq; Type: SEQUENCE OWNED BY; Schema: clinica; Owner: postgres
--

ALTER SEQUENCE clinica.registro_id_registro_seq OWNED BY clinica.registro.id_registro;


--
-- TOC entry 230 (class 1259 OID 16650)
-- Name: vista_pacientes; Type: VIEW; Schema: clinica; Owner: postgres
--

CREATE VIEW clinica.vista_pacientes AS
 SELECT i.id_ingreso,
    p.id_paciente,
    concat_ws(' '::text, p.nombre, p.apellido_paterno, p.apellido_materno) AS paciente,
    p.genero,
    p.fecha_nacimiento,
    (EXTRACT(year FROM age((CURRENT_DATE)::timestamp with time zone, (p.fecha_nacimiento)::timestamp with time zone)))::integer AS edad,
    i.peso,
    i.fecha_ingreso,
    i.hora_ingreso,
    i.estado,
    d.id_doctor,
    concat_ws(' '::text, d.nombre, d.apellido_paterno, d.apellido_materno) AS doctor,
    d.especialidad,
    r.alergias,
    r.observaciones AS observaciones_registro,
    r.diagnostico,
    r.salida,
    e.fecha_egreso,
    e.hora_egreso,
    e.observaciones AS observaciones_egreso
   FROM ((((clinica.ingreso i
     JOIN clinica.paciente p ON ((p.id_paciente = i.id_paciente)))
     LEFT JOIN clinica.registro r ON ((r.id_ingreso = i.id_ingreso)))
     LEFT JOIN clinica.doctor d ON ((d.id_doctor = r.id_doctor)))
     LEFT JOIN clinica.egreso e ON ((e.id_ingreso = i.id_ingreso)));


ALTER VIEW clinica.vista_pacientes OWNER TO postgres;

--
-- TOC entry 4805 (class 2604 OID 16692)
-- Name: detalle_receta id_detalle_receta; Type: DEFAULT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.detalle_receta ALTER COLUMN id_detalle_receta SET DEFAULT nextval('clinica.detalle_receta_id_detalle_receta_seq'::regclass);


--
-- TOC entry 4788 (class 2604 OID 16541)
-- Name: doctor id_doctor; Type: DEFAULT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.doctor ALTER COLUMN id_doctor SET DEFAULT nextval('clinica.doctor_id_doctor_seq'::regclass);


--
-- TOC entry 4800 (class 2604 OID 16624)
-- Name: egreso id_egreso; Type: DEFAULT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.egreso ALTER COLUMN id_egreso SET DEFAULT nextval('clinica.egreso_id_egreso_seq'::regclass);


--
-- TOC entry 4794 (class 2604 OID 16578)
-- Name: ingreso id_ingreso; Type: DEFAULT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.ingreso ALTER COLUMN id_ingreso SET DEFAULT nextval('clinica.ingreso_id_ingreso_seq'::regclass);


--
-- TOC entry 4785 (class 2604 OID 16528)
-- Name: paciente id_paciente; Type: DEFAULT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.paciente ALTER COLUMN id_paciente SET DEFAULT nextval('clinica.paciente_id_paciente_seq'::regclass);


--
-- TOC entry 4791 (class 2604 OID 16557)
-- Name: paciente_doctor id_paciente_doctor; Type: DEFAULT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.paciente_doctor ALTER COLUMN id_paciente_doctor SET DEFAULT nextval('clinica.paciente_doctor_id_paciente_doctor_seq'::regclass);


--
-- TOC entry 4802 (class 2604 OID 16674)
-- Name: receta id_receta; Type: DEFAULT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.receta ALTER COLUMN id_receta SET DEFAULT nextval('clinica.receta_id_receta_seq'::regclass);


--
-- TOC entry 4797 (class 2604 OID 16594)
-- Name: registro id_registro; Type: DEFAULT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.registro ALTER COLUMN id_registro SET DEFAULT nextval('clinica.registro_id_registro_seq'::regclass);


--
-- TOC entry 5028 (class 0 OID 16689)
-- Dependencies: 234
-- Data for Name: detalle_receta; Type: TABLE DATA; Schema: clinica; Owner: postgres
--

COPY clinica.detalle_receta (id_detalle_receta, id_receta, medicamento, dosis, frecuencia, duracion, via_administracion, indicaciones) FROM stdin;
1	1	Insulina	alta	cardica	3 dias	Intramuscular	ninguna
2	2	penicilina	media	5 horas	10 dias	Intravenosa	no hay
3	3	paracetamol	3 capsulas	cada 8 horas	6 dias	Oral	tyuio
4	4	insul	600	cada 2 dias	8 dias	Oral	fghj
5	5	NO HAY	NO HAY	NO HAY	NO HAY	Otra	NO HAY
6	3	fd	fd	fd	dfs	Oral	fd
7	9	paracetamol	capsulas de 500mg	cada 8 hrs	por 5 dias	Oral	no exceder la dosis ni sustituir el medicamento por uno similar
\.


--
-- TOC entry 5016 (class 0 OID 16538)
-- Dependencies: 221
-- Data for Name: doctor; Type: TABLE DATA; Schema: clinica; Owner: postgres
--

COPY clinica.doctor (id_doctor, nombre, apellido_paterno, apellido_materno, especialidad, cedula_profesional, telefono, correo, activo, fecha_registro) FROM stdin;
1	María	Hernández	López	Medicina general	CED-10001	9511234567	maria.hernandez@hospital.com	t	2026-07-16 18:39:35.469673
2	Carlos	Martínez	Ruiz	Medicina interna	CED-10002	9512345678	carlos.martinez@hospital.com	t	2026-07-16 18:39:35.469673
3	Ana	Sánchez	García	Pediatría	CED-10003	9513456789	ana.sanchez@hospital.com	t	2026-07-16 18:39:35.469673
4	Efrain	Garcia	López	Medicina Interna	CED123456	9511234567	efrain.lopez@hospital.com	t	2026-07-17 00:18:02.980126
6	Mayra	Matus	Martinez	Medicina General	CED123756	9511450667	matus.martinez@hospital.com	t	2026-07-17 00:19:57.743433
7	Eberardo	Aquino	Mendez	Anestesiologo	CED123789	9516452367	eberardo.mendez@hospital.com	t	2026-07-17 00:21:19.650047
\.


--
-- TOC entry 5024 (class 0 OID 16621)
-- Dependencies: 229
-- Data for Name: egreso; Type: TABLE DATA; Schema: clinica; Owner: postgres
--

COPY clinica.egreso (id_egreso, id_ingreso, fecha_egreso, hora_egreso, observaciones, fecha_creacion) FROM stdin;
1	1	2026-07-16	18:57:00	no hay	2026-07-16 18:58:40.853987
2	3	2026-07-16	19:45:00	en condiciones correctas	2026-07-16 19:45:28.485215
3	6	2026-07-16	23:50:00	en condiciones estables	2026-07-16 23:50:31.400664
4	7	2026-07-17	00:14:00	en estado conveniente	2026-07-17 00:14:26.545584
\.


--
-- TOC entry 5020 (class 0 OID 16575)
-- Dependencies: 225
-- Data for Name: ingreso; Type: TABLE DATA; Schema: clinica; Owner: postgres
--

COPY clinica.ingreso (id_ingreso, id_paciente, peso, fecha_ingreso, hora_ingreso, estado, fecha_creacion) FROM stdin;
1	1	63.00	2026-07-16	18:57:00	EGRESADO	2026-07-16 18:57:54.039369
2	2	60.00	2026-07-16	19:17:00	HOSPITALIZADO	2026-07-16 19:18:00.73311
3	3	68.00	2026-07-16	19:43:00	EGRESADO	2026-07-16 19:43:32.202981
4	4	50.00	2026-07-16	23:39:00	HOSPITALIZADO	2026-07-16 23:39:26.368286
5	5	45.00	2026-07-16	23:47:00	HOSPITALIZADO	2026-07-16 23:47:29.348049
6	6	40.00	2026-07-16	23:49:30	EGRESADO	2026-07-16 23:49:29.775289
7	7	10.00	2026-07-17	00:13:00	EGRESADO	2026-07-17 00:13:31.137098
9	9	15.00	2026-07-17	08:18:00	HOSPITALIZADO	2026-07-17 08:18:50.627286
10	10	14.00	2026-07-17	08:33:00	HOSPITALIZADO	2026-07-17 08:33:11.954748
12	12	2.00	2026-07-17	08:54:00	HOSPITALIZADO	2026-07-17 08:54:48.480486
8	8	3.00	2026-07-17	00:49:00	ALTA	2026-07-17 07:37:56.875826
13	13	30.00	2026-07-17	09:52:00	INGRESADO	2026-07-17 09:52:30.693046
11	11	8.00	2026-07-17	08:34:00	ALTA	2026-07-17 08:34:06.968937
14	14	58.00	2026-07-17	01:38:00	INGRESADO	2026-07-17 13:38:26.892664
15	15	70.00	2026-07-17	13:38:30	HOSPITALIZADO	2026-07-17 13:39:23.339339
\.


--
-- TOC entry 5014 (class 0 OID 16525)
-- Dependencies: 219
-- Data for Name: paciente; Type: TABLE DATA; Schema: clinica; Owner: postgres
--

COPY clinica.paciente (id_paciente, nombre, apellido_paterno, apellido_materno, genero, fecha_nacimiento, activo, fecha_registro) FROM stdin;
1	Daniela	Mendez	Uribe	Femenino	2006-03-26	t	2026-07-16 18:57:54.039369
2	Melisa	Hernandez	Antonio	Femenino	2000-07-17	t	2026-07-16 19:18:00.73311
3	Diego	Lopez	Perez	Masculino	2010-07-05	t	2026-07-16 19:43:32.202981
4	Melanie	Esteva	Gonzalez	Femenino	2000-07-08	t	2026-07-16 23:39:26.368286
5	Alejandro	Rios	Robles	Masculino	2018-07-13	t	2026-07-16 23:47:29.348049
6	Tadeo	Nuñez	Aquino	Masculino	2018-07-03	t	2026-07-16 23:49:29.775289
7	Joss	Anaya	Bautista	Femenino	2022-07-04	t	2026-07-17 00:13:31.137098
8	fedaf	feadfaf	fwrfwr	Femenino	2026-07-03	t	2026-07-17 07:37:56.875826
9	Alejandro	Rios	Ramirez	Masculino	2024-07-10	t	2026-07-17 08:18:50.627286
10	Natalia	Jimenez	Ruiz	Femenino	2017-07-05	t	2026-07-17 08:33:11.954748
11	Berenice	Anaya	Lopez	Femenino	2021-07-21	t	2026-07-17 08:34:06.968937
12	Carlos	Mtnz	Rios	Masculino	2024-07-10	t	2026-07-17 08:54:48.480486
13	Nahomi	Mnedez	Uribe	Femenino	2017-07-06	t	2026-07-17 09:52:30.693046
14	Ana	Perez	Garcia	Femenino	1980-04-23	t	2026-07-17 13:38:26.892664
15	Mario	Lopez	Perez	Masculino	2004-03-25	t	2026-07-17 13:39:23.339339
\.


--
-- TOC entry 5018 (class 0 OID 16554)
-- Dependencies: 223
-- Data for Name: paciente_doctor; Type: TABLE DATA; Schema: clinica; Owner: postgres
--

COPY clinica.paciente_doctor (id_paciente_doctor, id_paciente, id_doctor, fecha_asignacion, activo) FROM stdin;
1	1	1	2026-07-16 18:58:20.136187	t
2	2	1	2026-07-16 19:18:27.56055	t
3	3	2	2026-07-16 19:44:38.724242	t
4	4	1	2026-07-16 23:40:26.313456	t
5	5	2	2026-07-16 23:48:22.090222	t
6	6	1	2026-07-16 23:50:03.752001	t
7	7	3	2026-07-17 00:14:07.192306	t
8	8	7	2026-07-17 07:42:26.053739	t
9	9	1	2026-07-17 08:19:48.699275	t
10	10	7	2026-07-17 08:35:22.95505	t
11	11	4	2026-07-17 08:44:51.765222	t
12	12	4	2026-07-17 08:55:40.849363	t
13	8	1	2026-07-17 09:07:20.189874	t
16	11	2	2026-07-17 09:57:07.270408	t
17	15	4	2026-07-17 13:42:53.987452	t
\.


--
-- TOC entry 5026 (class 0 OID 16671)
-- Dependencies: 232
-- Data for Name: receta; Type: TABLE DATA; Schema: clinica; Owner: postgres
--

COPY clinica.receta (id_receta, id_registro, fecha_receta, indicaciones_generales, fecha_creacion) FROM stdin;
1	9	2026-07-17	no hay	2026-07-17 08:19:48.699275
2	10	2026-07-17	no hay	2026-07-17 08:35:22.95505
4	12	2026-07-17	gh	2026-07-17 08:55:40.849363
5	8	2026-07-17	NO HAY	2026-07-17 09:07:20.189874
3	11	2026-07-17	fd	2026-07-17 08:44:51.765222
9	17	2026-07-17	las que el medico señale	2026-07-17 13:42:53.987452
\.


--
-- TOC entry 5022 (class 0 OID 16591)
-- Dependencies: 227
-- Data for Name: registro; Type: TABLE DATA; Schema: clinica; Owner: postgres
--

COPY clinica.registro (id_registro, id_ingreso, id_paciente, id_doctor, alergias, observaciones, diagnostico, salida, fecha_registro, hora_registro) FROM stdin;
1	1	1	1	ninguna	ninguna	ninguna	ALTA	2026-07-16	18:58:20.136187
2	2	2	1	ninguna	no hay	sana	HOSPITALIZACION	2026-07-16	19:18:27.56055
3	3	3	2	el paciente presenta una alergia a la penicilina	ninguna	en estado critico	ALTA	2026-07-16	19:44:38.724242
4	4	4	1	ninguna	paciente en estado vulnerable	el que el medico indique	HOSPITALIZACION	2026-07-16	23:40:26.313456
5	5	5	2	alergias al polen	unicamente bajo observacion del medico asignado	fiebre severa	HOSPITALIZACION	2026-07-16	23:48:22.090222
6	6	6	1	no hay	ninguna	el que seleccione la doctora	ALTA	2026-07-16	23:50:03.752001
7	7	7	3	no presenta	ninguna	general	ALTA	2026-07-17	00:14:07.192306
9	9	9	1	ghsds	hjgs	fghj	HOSPITALIZACION	2026-07-17	08:19:48.699275
10	10	10	7	ghj	gtfd	aqws	HOSPITALIZACION	2026-07-17	08:35:22.95505
12	12	12	4	45	678yu	fghjk	HOSPITALIZACION	2026-07-17	08:55:40.849363
8	8	8	1	DFGH	DFGHJ	CHJK	ALTA	2026-07-17	07:42:26.053739
11	11	11	2	sdfg	dfg	sdfvg	ALTA	2026-07-17	08:44:51.765222
17	15	15	4	El paciente presenta Alergias a los frutos secos y farmacos fuertes como la penicilina	paciente en estado delicado	el paciente presenta fiebre a 40º	HOSPITALIZACION	2026-07-17	13:42:53.987452
\.


--
-- TOC entry 5042 (class 0 OID 0)
-- Dependencies: 233
-- Name: detalle_receta_id_detalle_receta_seq; Type: SEQUENCE SET; Schema: clinica; Owner: postgres
--

SELECT pg_catalog.setval('clinica.detalle_receta_id_detalle_receta_seq', 7, true);


--
-- TOC entry 5043 (class 0 OID 0)
-- Dependencies: 220
-- Name: doctor_id_doctor_seq; Type: SEQUENCE SET; Schema: clinica; Owner: postgres
--

SELECT pg_catalog.setval('clinica.doctor_id_doctor_seq', 7, true);


--
-- TOC entry 5044 (class 0 OID 0)
-- Dependencies: 228
-- Name: egreso_id_egreso_seq; Type: SEQUENCE SET; Schema: clinica; Owner: postgres
--

SELECT pg_catalog.setval('clinica.egreso_id_egreso_seq', 4, true);


--
-- TOC entry 5045 (class 0 OID 0)
-- Dependencies: 224
-- Name: ingreso_id_ingreso_seq; Type: SEQUENCE SET; Schema: clinica; Owner: postgres
--

SELECT pg_catalog.setval('clinica.ingreso_id_ingreso_seq', 15, true);


--
-- TOC entry 5046 (class 0 OID 0)
-- Dependencies: 222
-- Name: paciente_doctor_id_paciente_doctor_seq; Type: SEQUENCE SET; Schema: clinica; Owner: postgres
--

SELECT pg_catalog.setval('clinica.paciente_doctor_id_paciente_doctor_seq', 17, true);


--
-- TOC entry 5047 (class 0 OID 0)
-- Dependencies: 218
-- Name: paciente_id_paciente_seq; Type: SEQUENCE SET; Schema: clinica; Owner: postgres
--

SELECT pg_catalog.setval('clinica.paciente_id_paciente_seq', 15, true);


--
-- TOC entry 5048 (class 0 OID 0)
-- Dependencies: 231
-- Name: receta_id_receta_seq; Type: SEQUENCE SET; Schema: clinica; Owner: postgres
--

SELECT pg_catalog.setval('clinica.receta_id_receta_seq', 9, true);


--
-- TOC entry 5049 (class 0 OID 0)
-- Dependencies: 226
-- Name: registro_id_registro_seq; Type: SEQUENCE SET; Schema: clinica; Owner: postgres
--

SELECT pg_catalog.setval('clinica.registro_id_registro_seq', 17, true);


--
-- TOC entry 4854 (class 2606 OID 16699)
-- Name: detalle_receta detalle_receta_pkey; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.detalle_receta
    ADD CONSTRAINT detalle_receta_pkey PRIMARY KEY (id_detalle_receta);


--
-- TOC entry 4825 (class 2606 OID 16548)
-- Name: doctor pk_doctor; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.doctor
    ADD CONSTRAINT pk_doctor PRIMARY KEY (id_doctor);


--
-- TOC entry 4846 (class 2606 OID 16629)
-- Name: egreso pk_egreso; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.egreso
    ADD CONSTRAINT pk_egreso PRIMARY KEY (id_egreso);


--
-- TOC entry 4837 (class 2606 OID 16584)
-- Name: ingreso pk_ingreso; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.ingreso
    ADD CONSTRAINT pk_ingreso PRIMARY KEY (id_ingreso);


--
-- TOC entry 4822 (class 2606 OID 16536)
-- Name: paciente pk_paciente; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.paciente
    ADD CONSTRAINT pk_paciente PRIMARY KEY (id_paciente);


--
-- TOC entry 4831 (class 2606 OID 16561)
-- Name: paciente_doctor pk_paciente_doctor; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.paciente_doctor
    ADD CONSTRAINT pk_paciente_doctor PRIMARY KEY (id_paciente_doctor);


--
-- TOC entry 4841 (class 2606 OID 16602)
-- Name: registro pk_registro; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.registro
    ADD CONSTRAINT pk_registro PRIMARY KEY (id_registro);


--
-- TOC entry 4850 (class 2606 OID 16680)
-- Name: receta receta_pkey; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.receta
    ADD CONSTRAINT receta_pkey PRIMARY KEY (id_receta);


--
-- TOC entry 4827 (class 2606 OID 16550)
-- Name: doctor uq_doctor_cedula; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.doctor
    ADD CONSTRAINT uq_doctor_cedula UNIQUE (cedula_profesional);


--
-- TOC entry 4829 (class 2606 OID 16552)
-- Name: doctor uq_doctor_correo; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.doctor
    ADD CONSTRAINT uq_doctor_correo UNIQUE (correo);


--
-- TOC entry 4848 (class 2606 OID 16631)
-- Name: egreso uq_egreso_ingreso; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.egreso
    ADD CONSTRAINT uq_egreso_ingreso UNIQUE (id_ingreso);


--
-- TOC entry 4833 (class 2606 OID 16563)
-- Name: paciente_doctor uq_paciente_doctor; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.paciente_doctor
    ADD CONSTRAINT uq_paciente_doctor UNIQUE (id_paciente, id_doctor);


--
-- TOC entry 4852 (class 2606 OID 16682)
-- Name: receta uq_receta_registro; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.receta
    ADD CONSTRAINT uq_receta_registro UNIQUE (id_registro);


--
-- TOC entry 4843 (class 2606 OID 16604)
-- Name: registro uq_registro_ingreso; Type: CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.registro
    ADD CONSTRAINT uq_registro_ingreso UNIQUE (id_ingreso);


--
-- TOC entry 4823 (class 1259 OID 16644)
-- Name: idx_doctor_nombre; Type: INDEX; Schema: clinica; Owner: postgres
--

CREATE INDEX idx_doctor_nombre ON clinica.doctor USING btree (apellido_paterno, apellido_materno, nombre);


--
-- TOC entry 4844 (class 1259 OID 16649)
-- Name: idx_egreso_fecha; Type: INDEX; Schema: clinica; Owner: postgres
--

CREATE INDEX idx_egreso_fecha ON clinica.egreso USING btree (fecha_egreso);


--
-- TOC entry 4834 (class 1259 OID 16646)
-- Name: idx_ingreso_estado; Type: INDEX; Schema: clinica; Owner: postgres
--

CREATE INDEX idx_ingreso_estado ON clinica.ingreso USING btree (estado);


--
-- TOC entry 4835 (class 1259 OID 16645)
-- Name: idx_ingreso_paciente; Type: INDEX; Schema: clinica; Owner: postgres
--

CREATE INDEX idx_ingreso_paciente ON clinica.ingreso USING btree (id_paciente);


--
-- TOC entry 4820 (class 1259 OID 16643)
-- Name: idx_paciente_nombre; Type: INDEX; Schema: clinica; Owner: postgres
--

CREATE INDEX idx_paciente_nombre ON clinica.paciente USING btree (apellido_paterno, apellido_materno, nombre);


--
-- TOC entry 4838 (class 1259 OID 16648)
-- Name: idx_registro_doctor; Type: INDEX; Schema: clinica; Owner: postgres
--

CREATE INDEX idx_registro_doctor ON clinica.registro USING btree (id_doctor);


--
-- TOC entry 4839 (class 1259 OID 16647)
-- Name: idx_registro_paciente; Type: INDEX; Schema: clinica; Owner: postgres
--

CREATE INDEX idx_registro_paciente ON clinica.registro USING btree (id_paciente);


--
-- TOC entry 4865 (class 2620 OID 16640)
-- Name: egreso trg_actualizar_estado_egreso; Type: TRIGGER; Schema: clinica; Owner: postgres
--

CREATE TRIGGER trg_actualizar_estado_egreso AFTER INSERT ON clinica.egreso FOR EACH ROW EXECUTE FUNCTION clinica.actualizar_estado_egreso();


--
-- TOC entry 4864 (class 2620 OID 16638)
-- Name: registro trg_actualizar_estado_registro; Type: TRIGGER; Schema: clinica; Owner: postgres
--

CREATE TRIGGER trg_actualizar_estado_registro AFTER INSERT OR UPDATE OF salida ON clinica.registro FOR EACH ROW EXECUTE FUNCTION clinica.actualizar_estado_registro();


--
-- TOC entry 4866 (class 2620 OID 16642)
-- Name: egreso trg_validar_fecha_egreso; Type: TRIGGER; Schema: clinica; Owner: postgres
--

CREATE TRIGGER trg_validar_fecha_egreso BEFORE INSERT OR UPDATE ON clinica.egreso FOR EACH ROW EXECUTE FUNCTION clinica.validar_fecha_egreso();


--
-- TOC entry 4863 (class 2606 OID 16700)
-- Name: detalle_receta fk_detalle_receta; Type: FK CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.detalle_receta
    ADD CONSTRAINT fk_detalle_receta FOREIGN KEY (id_receta) REFERENCES clinica.receta(id_receta) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4861 (class 2606 OID 16632)
-- Name: egreso fk_egreso_ingreso; Type: FK CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.egreso
    ADD CONSTRAINT fk_egreso_ingreso FOREIGN KEY (id_ingreso) REFERENCES clinica.ingreso(id_ingreso) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4857 (class 2606 OID 16585)
-- Name: ingreso fk_ingreso_paciente; Type: FK CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.ingreso
    ADD CONSTRAINT fk_ingreso_paciente FOREIGN KEY (id_paciente) REFERENCES clinica.paciente(id_paciente) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4855 (class 2606 OID 16569)
-- Name: paciente_doctor fk_paciente_doctor_doctor; Type: FK CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.paciente_doctor
    ADD CONSTRAINT fk_paciente_doctor_doctor FOREIGN KEY (id_doctor) REFERENCES clinica.doctor(id_doctor) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4856 (class 2606 OID 16564)
-- Name: paciente_doctor fk_paciente_doctor_paciente; Type: FK CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.paciente_doctor
    ADD CONSTRAINT fk_paciente_doctor_paciente FOREIGN KEY (id_paciente) REFERENCES clinica.paciente(id_paciente) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4862 (class 2606 OID 16683)
-- Name: receta fk_receta_registro; Type: FK CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.receta
    ADD CONSTRAINT fk_receta_registro FOREIGN KEY (id_registro) REFERENCES clinica.registro(id_registro) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4858 (class 2606 OID 16615)
-- Name: registro fk_registro_doctor; Type: FK CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.registro
    ADD CONSTRAINT fk_registro_doctor FOREIGN KEY (id_doctor) REFERENCES clinica.doctor(id_doctor) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4859 (class 2606 OID 16605)
-- Name: registro fk_registro_ingreso; Type: FK CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.registro
    ADD CONSTRAINT fk_registro_ingreso FOREIGN KEY (id_ingreso) REFERENCES clinica.ingreso(id_ingreso) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4860 (class 2606 OID 16610)
-- Name: registro fk_registro_paciente; Type: FK CONSTRAINT; Schema: clinica; Owner: postgres
--

ALTER TABLE ONLY clinica.registro
    ADD CONSTRAINT fk_registro_paciente FOREIGN KEY (id_paciente) REFERENCES clinica.paciente(id_paciente) ON UPDATE CASCADE ON DELETE RESTRICT;


-- Completed on 2026-07-27 23:05:32

--
-- PostgreSQL database dump complete
--

\unrestrict X1OfqT5BWi3GbRNpFtmzpLeYHpDJn70je3uEy1oBdy2SAB21ZKb55Kv6iXYh00z

