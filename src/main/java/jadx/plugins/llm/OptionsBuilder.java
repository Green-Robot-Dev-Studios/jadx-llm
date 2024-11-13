package jadx.plugins.llm;

import jadx.api.plugins.options.impl.BasePluginOptionsBuilder;

public class OptionsBuilder extends BasePluginOptionsBuilder {

	private boolean enable;
	private String keybind;
	
	private String modelName;
	private String apiKey;
	private String baseUrl;
	private int maxTokens;

	@Override
	public void registerOptions() {
		boolOption(JadxLLM.PLUGIN_ID + ".enable")
				.description("enable comment")
				.defaultValue(true)
				.setter(v -> enable = v);
		strOption(JadxLLM.PLUGIN_ID + ".keybind")
				.description("keybind")
				.defaultValue("I")
				.setter(v -> keybind = v);
		strOption(JadxLLM.PLUGIN_ID + ".model_name")
				.description("model name")
				.defaultValue("gpt-3.5-turbo")
				.setter(v -> modelName = v);
		strOption(JadxLLM.PLUGIN_ID + ".api_key")
				.description("api key")
				.defaultValue("123456")
				.setter(v -> apiKey = v);
		strOption(JadxLLM.PLUGIN_ID + ".base_url")
				.description("base url")
				.defaultValue("https://api.openai.com")
				.setter(v -> baseUrl = v);
		// intOption(JadxLLM.PLUGIN_ID + ".max_tokens")
		// 		.description("max tokens")
		// 		.defaultValue(10000)
		// 		.setter(v -> maxTokens = v);
	}

	public boolean isEnable() {
		return enable;
	}

	public String getKeybind() {
		return keybind;
	}

	public String getModelName() {
		return modelName;
	}

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
		return baseUrl;
	}

    public int getMaxTokens() {
		//TODO: does not run due to missing method
		return 10000;
	}
}
