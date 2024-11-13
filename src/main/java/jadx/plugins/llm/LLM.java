package jadx.plugins.llm;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class LLM {
    private ChatLanguageModel model;

    public LLM(String modelName, String apiKey, String baseUrl, int maxTokens) {
        model = OpenAiChatModel.builder()
            .maxTokens(maxTokens)
            .baseUrl(baseUrl)
            .apiKey(apiKey)
            .modelName(modelName)
            .build();
    }

    public String ask(String question) {
        String answer = model.generate(question);

        return answer;
    }
}
