package vn.edu.usth.englishdictionary.utils;

import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import java.util.List;

import java.util.ArrayList;

public class api {
    public static String apiMethod(String message) {
        OpenAiService service = new OpenAiService("");
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "Paraphrase this paragraph");
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), message);
        messages.add(systemMessage);
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo")
                .build();

        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();

        // Kiểm tra nếu responseMessage không null và có nội dung
        if (responseMessage != null && responseMessage.getContent() != null) {
            return responseMessage.getContent();
        } else {
            return "Không có dữ liệu trả về"; // hoặc giá trị mặc định khác bạn muốn
        }
    }
}