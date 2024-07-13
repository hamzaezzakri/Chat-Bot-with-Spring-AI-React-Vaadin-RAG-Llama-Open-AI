package net.ezzakri.ensetchatbotrag.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ChatAiService {

    private ChatClient chatClient;
    private VectorStore vectorStore;
    @Value("classpath:/prompts/prompt-template.ts")
    private Resource resrouce;

    public ChatAiService(ChatClient.Builder builder, VectorStore vectorStore){

        this.chatClient = builder.build();
        this.vectorStore = vectorStore;
    }

    public String ragChat(String question){
        List<Document> documents = vectorStore.similaritySearch(question);
        List<String> context = documents.stream().map(Document::getContent).toList();
        PromptTemplate promptTemplate = new PromptTemplate(String.valueOf(resrouce));
        Prompt prompt = promptTemplate.create(Map.of("context", context , "question", question));

        return chatClient.prompt(prompt)
                .call()
                .content();
    }
}
