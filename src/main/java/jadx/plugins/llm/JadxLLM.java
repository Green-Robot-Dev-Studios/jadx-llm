package jadx.plugins.llm;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import jadx.api.JavaMethod;
import jadx.api.JavaNode;
import jadx.api.metadata.ICodeNodeRef;
import jadx.api.metadata.ICodeAnnotation.AnnType;
import jadx.api.plugins.JadxPlugin;
import jadx.api.plugins.JadxPluginContext;
import jadx.api.plugins.JadxPluginInfo;
import jadx.api.plugins.JadxPluginInfoBuilder;
import jadx.api.plugins.events.types.NodeRenamedByUser;

public class JadxLLM implements JadxPlugin {
	public static final String PLUGIN_ID = "jadx-llm";

	private final OptionsBuilder options = new OptionsBuilder();

	@Override
	public JadxPluginInfo getPluginInfo() {
		return JadxPluginInfoBuilder.pluginId(PLUGIN_ID)
				.name("JADX LLM")
				.description("LLM AI for jadx")
				.homepage("https://github.com/green-robot/jadx-llm")
				.build();
	}

	@Override
	public void init(JadxPluginContext context) {
		context.registerOptions(options);
		
		if (!options.isEnable()) {
			return;
		}
		
		Function<ICodeNodeRef, Boolean> isFunc = (ICodeNodeRef node) -> node.getAnnType() == AnnType.METHOD;

		Consumer<ICodeNodeRef> renameAI = (ICodeNodeRef node) -> {
			String code;
			JavaNode jNode = context.getDecompiler().getJavaNodeByRef(node);
			if (jNode instanceof JavaMethod) {
				code = ((JavaMethod) jNode).getDeclaringClass().getCode();
			} else {
				return;
			}

			LLM llm = new LLM(options.getModelName(), options.getApiKey(), options.getBaseUrl(), options.getMaxTokens());
			
			String prompt = "You are the foremost expert on Java decompilation and deobfuscation. Analyze the following class, and come up with a name that makes most sense for the function called " + jNode.getName() + ". It is absolutely imperative that you answer with ONLY the name of the function. Here is the class: " + code;
			String newName = llm.ask(prompt);

			SwingUtilities.invokeLater(() ->
					JOptionPane.showMessageDialog(null, prompt, "Error", JOptionPane.ERROR_MESSAGE)
			);

			NodeRenamedByUser event = new NodeRenamedByUser(node, "", newName);
			event.setRenameNode(node);
			event.setResetName(false);

			context.events().send(event);
		};

		context.getGuiContext().addPopupMenuAction("AI Rename", isFunc, options.getKeybind(), renameAI);
	}
}
