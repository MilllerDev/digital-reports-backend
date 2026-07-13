package com.uni.digitalreports.ai.reports.adapter;

import com.uni.digitalreports.ai.reports.dto.DailySummaryResult;
import com.uni.digitalreports.ai.reports.dto.DuplicateCheckResult;
import com.uni.digitalreports.ai.reports.dto.SpamCheckResult;
import com.uni.digitalreports.ai.reports.port.AiAnalysisPort;
import com.uni.digitalreports.reports.domain.model.Report;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeminiAnalysisAdapter implements AiAnalysisPort {
    private final ChatClient chatClient;

    public GeminiAnalysisAdapter(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public SpamCheckResult checkSpam(String asunto, String description) {
        return chatClient.prompt()
                .user(u -> u.text("""
                                Analiza si el siguiente reporte ciudadano es spam, contenido sin sentido,
                                publicidad (enlaces comerciales o promociones), o abuso del sistema.
                                No es spam si describe un problema real relacionado servicio público aunque
                                esté mal redactado gramaticalmente u ortográficamente.
                                Título: {asunto}
                                Descripción: {description}
                                """)
                        .param("asunto", asunto)
                        .param("description", description))
                .call()
                .entity(SpamCheckResult.class);
    }

    @Override
    public DuplicateCheckResult checkDuplicate(String description, double latitude, double longitude, List<Report> reports) {
        String candidatesText = reports.stream()
                .map(r -> "ID: %s | Descripción: %s".formatted(r.getId(), r.getDescription()))
                .reduce("", (a, b) -> a + "\n" + b);
        return chatClient.prompt()
                .user(u -> u.text("""
                                Un nuevo reporte dice: "{description}"
                                Compáralo con estos reportes cercanos (aproximado de 100) ya existentes (mismo radio geográfico):
                                {candidates}
                                Determina si el nuevo reporte es un DUPLICADO de alguno de estos
                                (describe el mismo problema, aunque con palabras distintas).
                                Si es duplicado, indica el ID exacto del reporte original.
                                """)
                        .param("description", description)
                        .param("candidates", candidatesText))
                .call()
                .entity(DuplicateCheckResult.class);
    }

    @Override
    public DailySummaryResult makeSummary(List<Report> reports) {
        String reportsText = reports.stream()
                .map(r -> "Título: %s | Descripción: %s | Estado: %s | Prioridad: %s | Dirección: %s"
                        .formatted(r.getAsunto(), r.getDescription(),
                                r.getStatus(), r.getImportant(), r.getAddress()))
                .reduce("", (a, b) -> a + "\n" + b);

        return chatClient.prompt()
                .user(u -> u.text("""
                                Genera un resumen ejecutivo del día de hoy con los siguientes reportes ciudadanos
                                de Huacho, Lima, Perú. Estos son todos los reportes recibidos desde el inicio
                                del día hasta el momento actual.
                                Criterios para generar el resumen:
                                - mainCategories: Identifica las categorías de problemas más frecuentes
                                  (ej: baches, alumbrado, seguridad, limpieza, inundaciones, etc.)
                                - hotspots: Identifica las zonas o direcciones que concentran más reportes
                                - recommendations: sugiere acciones concretas para las autoridades municipales
                                  basadas en la urgencia y volumen de los reportes
                                - Si no hay reportes, devuelve totalReports en 0 y un summary indicando
                                  que no se registraron incidencias en el día
                                Reportes del día:
                                {reports}
                                """)
                        .param("reports", reportsText))
                .call()
                .entity(DailySummaryResult.class);
    }
}
