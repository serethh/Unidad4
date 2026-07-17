package Interfaz;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public final class GestionPDF {
    
 

    private static final float MARGEN = 50;
    private static final float ANCHO_CONTENIDO =
            PDRectangle.LETTER.getWidth() - (MARGEN * 2);

    private static final PDFont FUENTE_NORMAL =
            new PDType1Font(
                    Standard14Fonts.FontName.HELVETICA
            );

    private static final PDFont FUENTE_NEGRITA =
            new PDType1Font(
                    Standard14Fonts.FontName.HELVETICA_BOLD
            );

    private static final PDFont FUENTE_CURSIVA =
            new PDType1Font(
                    Standard14Fonts.FontName.HELVETICA_OBLIQUE
            );

    private GestionPDF() {
        // Evita crear objetos de esta clase.
    }

    /**
     * Genera una receta médica en PDF y la guarda
     * en el Escritorio del usuario.
     *
     * @return ruta completa del PDF creado
     */
    public static Path generarReceta(
            String paciente,
            String doctor,
            String alergias,
            String diagnostico,
            String medicamento,
            String dosis,
            String frecuencia,
            String duracion,
            String via,
            String indicaciones,
            String indicacionesGenerales
    ) throws IOException {

        validarDatoObligatorio(
                paciente,
                "El nombre del paciente es obligatorio."
        );

        validarDatoObligatorio(
                doctor,
                "El nombre del doctor es obligatorio."
        );

        validarDatoObligatorio(
                diagnostico,
                "El diagnóstico es obligatorio."
        );

        validarDatoObligatorio(
                medicamento,
                "El medicamento es obligatorio."
        );

        validarDatoObligatorio(
                dosis,
                "La dosis es obligatoria."
        );

        validarDatoObligatorio(
                frecuencia,
                "La frecuencia es obligatoria."
        );

        Path escritorio = obtenerEscritorio();

        Files.createDirectories(escritorio);

        String nombreArchivo =
                "Receta_"
                + limpiarNombreArchivo(paciente)
                + "_"
                + LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd"
                                )
                        )
                + ".pdf";

        Path rutaPDF =
                obtenerRutaDisponible(
                        escritorio.resolve(nombreArchivo)
                );

        try (PDDocument documento = new PDDocument()) {

            PDPage pagina =
                    new PDPage(PDRectangle.LETTER);

            documento.addPage(pagina);

            try (
                PDPageContentStream contenido =
                        new PDPageContentStream(
                                documento,
                                pagina
                        )
            ) {

                dibujarEncabezado(contenido);

                float y = 670;

                y = escribirEtiquetaValor(
                        contenido,
                        "Paciente:",
                        paciente,
                        y
                );

                y = escribirEtiquetaValor(
                        contenido,
                        "Doctor:",
                        doctor,
                        y
                );

                y = escribirEtiquetaValor(
                        contenido,
                        "Fecha:",
                        LocalDate.now().format(
                                DateTimeFormatter.ofPattern(
                                        "dd/MM/yyyy"
                                )
                        ),
                        y
                );

                dibujarLinea(
                        contenido,
                        MARGEN,
                        y - 5,
                        PDRectangle.LETTER.getWidth()
                                - MARGEN,
                        y - 5
                );

                y -= 35;

                y = escribirTituloSeccion(
                        contenido,
                        "INFORMACIÓN CLÍNICA",
                        y
                );

                y = escribirBloque(
                        contenido,
                        "Diagnóstico",
                        valorONoEspecificado(
                                diagnostico
                        ),
                        y
                );

                y = escribirBloque(
                        contenido,
                        "Alergias",
                        valorONoEspecificado(
                                alergias
                        ),
                        y
                );

                y -= 5;

                y = escribirTituloSeccion(
                        contenido,
                        "TRATAMIENTO INDICADO",
                        y
                );

                y = escribirEtiquetaValor(
                        contenido,
                        "Medicamento:",
                        medicamento,
                        y
                );

                y = escribirEtiquetaValor(
                        contenido,
                        "Dosis:",
                        dosis,
                        y
                );

                y = escribirEtiquetaValor(
                        contenido,
                        "Frecuencia:",
                        frecuencia,
                        y
                );

                y = escribirEtiquetaValor(
                        contenido,
                        "Duración:",
                        valorONoEspecificado(
                                duracion
                        ),
                        y
                );

                y = escribirEtiquetaValor(
                        contenido,
                        "Vía de administración:",
                        valorONoEspecificado(
                                via
                        ),
                        y
                );

                y -= 10;

                y = escribirBloque(
                        contenido,
                        "Indicaciones del medicamento",
                        valorONoEspecificado(
                                indicaciones
                        ),
                        y
                );

                y = escribirBloque(
                        contenido,
                        "Indicaciones generales",
                        valorONoEspecificado(
                                indicacionesGenerales
                        ),
                        y
                );

                dibujarFirma(
                        contenido,
                        doctor
                );

                dibujarPiePagina(
                        contenido
                );
            }

            documento.save(
                    rutaPDF.toFile()
            );
        }

        return rutaPDF;
    }

    /**
     * Intenta abrir automáticamente el PDF creado.
     */
    public static void abrirPDF(
            Path rutaPDF
    ) throws IOException {

        if (rutaPDF == null
                || !Files.exists(rutaPDF)) {

            throw new IOException(
                    "El archivo PDF no existe."
            );
        }

        if (Desktop.isDesktopSupported()) {

            Desktop desktop =
                    Desktop.getDesktop();

            if (desktop.isSupported(
                    Desktop.Action.OPEN
            )) {

                desktop.open(
                        rutaPDF.toFile()
                );
            }
        }
    }

    private static void dibujarEncabezado(
            PDPageContentStream contenido
    ) throws IOException {

        float anchoPagina =
                PDRectangle.LETTER.getWidth();

        contenido.setNonStrokingColor(
                21f / 255f,
                101f / 255f,
                192f / 255f
        );

        contenido.addRect(
                0,
                710,
                anchoPagina,
                82
        );

        contenido.fill();

        contenido.setNonStrokingColor(
                1f,
                1f,
                1f
        );

        escribirTexto(
                contenido,
                "HOSPITAL",
                FUENTE_NEGRITA,
                24,
                MARGEN,
                754
        );

        escribirTexto(
                contenido,
                "RECETA MÉDICA",
                FUENTE_NEGRITA,
                16,
                MARGEN,
                730
        );

        contenido.setNonStrokingColor(
                0f,
                0f,
                0f
        );
    }

    private static float escribirTituloSeccion(
            PDPageContentStream contenido,
            String titulo,
            float y
    ) throws IOException {

        contenido.setNonStrokingColor(
                234f / 255f,
                244f / 255f,
                255f / 255f
        );

        contenido.addRect(
                MARGEN,
                y - 5,
                ANCHO_CONTENIDO,
                24
        );

        contenido.fill();

        contenido.setNonStrokingColor(
                21f / 255f,
                101f / 255f,
                192f / 255f
        );

        escribirTexto(
                contenido,
                titulo,
                FUENTE_NEGRITA,
                12,
                MARGEN + 8,
                y + 2
        );

        contenido.setNonStrokingColor(
                0f,
                0f,
                0f
        );

        return y - 35;
    }

    private static float escribirEtiquetaValor(
            PDPageContentStream contenido,
            String etiqueta,
            String valor,
            float y
    ) throws IOException {

        float anchoEtiqueta = 145;

        escribirTexto(
                contenido,
                etiqueta,
                FUENTE_NEGRITA,
                11,
                MARGEN,
                y
        );

        String texto =
                valorONoEspecificado(valor);

        float xValor =
                MARGEN + anchoEtiqueta;

        float anchoValor =
                ANCHO_CONTENIDO - anchoEtiqueta;

        return escribirTextoAjustado(
                contenido,
                texto,
                FUENTE_NORMAL,
                11,
                xValor,
                y,
                anchoValor,
                15
        ) - 6;
    }

    private static float escribirBloque(
            PDPageContentStream contenido,
            String titulo,
            String texto,
            float y
    ) throws IOException {

        escribirTexto(
                contenido,
                titulo + ":",
                FUENTE_NEGRITA,
                11,
                MARGEN,
                y
        );

        y -= 17;

        y = escribirTextoAjustado(
                contenido,
                texto,
                FUENTE_NORMAL,
                10,
                MARGEN + 10,
                y,
                ANCHO_CONTENIDO - 20,
                14
        );

        return y - 12;
    }

    private static float escribirTextoAjustado(
            PDPageContentStream contenido,
            String texto,
            PDFont fuente,
            float tamano,
            float x,
            float y,
            float anchoMaximo,
            float interlineado
    ) throws IOException {

        String textoSeguro =
                limpiarTextoPDF(
                        valorONoEspecificado(texto)
                );

        String[] parrafos =
                textoSeguro.split("\\R", -1);

        float posicionY = y;

        for (String parrafo : parrafos) {

            if (parrafo.isBlank()) {
                posicionY -= interlineado;
                continue;
            }

            String[] palabras =
                    parrafo.trim().split("\\s+");

            StringBuilder linea =
                    new StringBuilder();

            for (String palabra : palabras) {

                String prueba =
                        linea.length() == 0
                        ? palabra
                        : linea + " " + palabra;

                float anchoTexto =
                        fuente.getStringWidth(prueba)
                        / 1000f
                        * tamano;

                if (anchoTexto > anchoMaximo
                        && linea.length() > 0) {

                    escribirTexto(
                            contenido,
                            linea.toString(),
                            fuente,
                            tamano,
                            x,
                            posicionY
                    );

                    posicionY -= interlineado;

                    linea = new StringBuilder(
                            palabra
                    );

                } else {

                    if (linea.length() > 0) {
                        linea.append(" ");
                    }

                    linea.append(palabra);
                }
            }

            if (linea.length() > 0) {

                escribirTexto(
                        contenido,
                        linea.toString(),
                        fuente,
                        tamano,
                        x,
                        posicionY
                );

                posicionY -= interlineado;
            }
        }

        return posicionY;
    }

    private static void escribirTexto(
            PDPageContentStream contenido,
            String texto,
            PDFont fuente,
            float tamano,
            float x,
            float y
    ) throws IOException {

        contenido.beginText();

        contenido.setFont(
                fuente,
                tamano
        );

        contenido.newLineAtOffset(
                x,
                y
        );

        contenido.showText(
                limpiarTextoPDF(texto)
        );

        contenido.endText();
    }

    private static void dibujarFirma(
            PDPageContentStream contenido,
            String doctor
    ) throws IOException {

        float centro =
                PDRectangle.LETTER.getWidth()
                / 2;

        float yLinea = 105;

        dibujarLinea(
                contenido,
                centro - 120,
                yLinea,
                centro + 120,
                yLinea
        );

        String nombreDoctor =
                "Dr(a). "
                + valorONoEspecificado(doctor);

        float anchoDoctor =
                FUENTE_NEGRITA.getStringWidth(
                        limpiarTextoPDF(nombreDoctor)
                )
                / 1000f
                * 10;

        escribirTexto(
                contenido,
                nombreDoctor,
                FUENTE_NEGRITA,
                10,
                centro - (anchoDoctor / 2),
                yLinea - 17
        );

        String firma =
                "Nombre y firma del médico";

        float anchoFirma =
                FUENTE_CURSIVA.getStringWidth(
                        firma
                )
                / 1000f
                * 9;

        escribirTexto(
                contenido,
                firma,
                FUENTE_CURSIVA,
                9,
                centro - (anchoFirma / 2),
                yLinea - 31
        );
    }

    private static void dibujarPiePagina(
            PDPageContentStream contenido
    ) throws IOException {

        dibujarLinea(
                contenido,
                MARGEN,
                48,
                PDRectangle.LETTER.getWidth()
                        - MARGEN,
                48
        );

        escribirTexto(
                contenido,
                "Documento generado por el Sistema Hospitalario",
                FUENTE_CURSIVA,
                8,
                MARGEN,
                32
        );
    }

    private static void dibujarLinea(
            PDPageContentStream contenido,
            float xInicial,
            float yInicial,
            float xFinal,
            float yFinal
    ) throws IOException {

        contenido.setStrokingColor(
                120f / 255f,
                120f / 255f,
                120f / 255f
        );

        contenido.setLineWidth(
                0.7f
        );

        contenido.moveTo(
                xInicial,
                yInicial
        );

        contenido.lineTo(
                xFinal,
                yFinal
        );

        contenido.stroke();

        contenido.setStrokingColor(
                0f,
                0f,
                0f
        );
    }

    private static Path obtenerEscritorio() {

        Path escritorio =
                Paths.get(
                        System.getProperty(
                                "user.home"
                        ),
                        "Desktop"
                );

        if (Files.isDirectory(escritorio)) {
            return escritorio;
        }

        return Paths.get(
                System.getProperty(
                        "user.home"
                )
        );
    }

    private static Path obtenerRutaDisponible(
            Path rutaOriginal
    ) {

        if (!Files.exists(rutaOriginal)) {
            return rutaOriginal;
        }

        String nombre =
                rutaOriginal.getFileName()
                        .toString();

        int punto =
                nombre.lastIndexOf('.');

        String nombreBase =
                punto > 0
                ? nombre.substring(0, punto)
                : nombre;

        String extension =
                punto > 0
                ? nombre.substring(punto)
                : "";

        int numero = 2;

        Path carpeta =
                rutaOriginal.getParent();

        Path nuevaRuta;

        do {

            nuevaRuta = carpeta.resolve(
                    nombreBase
                    + "_"
                    + numero
                    + extension
            );

            numero++;

        } while (Files.exists(nuevaRuta));

        return nuevaRuta;
    }

    private static String limpiarNombreArchivo(
            String texto
    ) {

        String normalizado =
                Normalizer.normalize(
                        valorONoEspecificado(texto),
                        Normalizer.Form.NFD
                );

        return normalizado
                .replaceAll("\\p{M}", "")
                .replaceAll("[^a-zA-Z0-9]+", "_")
                .replaceAll("^_+|_+$", "");
    }

    /*
     * Las fuentes Helvetica estándar de PDFBox no contienen
     * todos los caracteres Unicode. Se reemplazan algunos
     * caracteres para evitar errores al generar el PDF.
     */
    private static String limpiarTextoPDF(
            String texto
    ) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("–", "-")
                .replace("—", "-")
                .replace("“", "\"")
                .replace("”", "\"")
                .replace("‘", "'")
                .replace("’", "'")
                .replace("•", "-")
                .replace("\t", " ");
    }

    private static String valorONoEspecificado(
            String texto
    ) {

        return texto == null
                || texto.isBlank()
                ? "No especificado"
                : texto.trim();
    }

    private static void validarDatoObligatorio(
            String valor,
            String mensaje
    ) {

        if (valor == null
                || valor.isBlank()) {

            throw new IllegalArgumentException(
                    mensaje
            );
        }
    }
}