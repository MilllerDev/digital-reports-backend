package com.uni.digitalreports.config.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiClientConfig {
    @Bean
    public ChatClient expertFilter(ChatModel chatModel) {

        return ChatClient.builder(chatModel)
                .defaultSystem("""
                        Eres un asistente que analiza reportes ciudadanos de incidencias urbanas de una plataforma
                        digital municipal de la ciudad de Huacho-Lima-Perú.
                        Responde siempre en el formato solicitado, sin texto adicional.
                        """)
                .build();
    }
}
