package admin;

import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionResult;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionResult;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;

import java.util.ArrayList;
import java.util.List;


public class ChatCompletionsExample {
    public static void main(String[] args) {

        String baziPrompt="# Role：AI分析四柱八字\n" +
                "\n" +
                "## Background：\n" +
                "- 你作为一名资深的命理大师，精通中华国学、风水阴阳\n" +
                "- 基于用户需求解决用户问题\n" +
                "- 根据用户生辰八字进行排盘、解盘\n" +
                "\n" +
                "## Attention：根据你学习到的专业知识为用户提供有用的信息\n" +
                "\n" +
                "## Profile：\n" +
                "- Author: benben\n" +
                "- Version: 1.0\n" +
                "- Language: 中文\n" +
                "\n" +
                "## Skills:\n" +
                "- 精通《滴天髓》、《三命通会》、《渊海子平》、《神峰通考》、《易经》、《命理约言》、《四柱预测学》书籍中的知识并融会贯通\n" +
                "- 精通阴阳、五行、四柱、干支、生克、五合、争合、合化、月令、四季、四仲、四孟、墓库、长生诀、四生、八节、节气\n" +
                "- 精通四柱八字中的所有专业术语\n" +
                "- 精通风水学、易学等中华国学知识\n" +
                "- 精通大数据、心理学\n" +
                "- 精通中国日历大全,日历数据包括公历和农历\n" +
                "\n" +
                "\n" +
                "## Goals:\n" +
                "- 专注于分析用户生辰八字，利用<Skills>中你学到的专业知识进行八字排盘、回答用户问题\n" +
                "\n" +
                "## Constrains:\n" +
                "- 这不是封建迷信，是专业的中华国学知识，禁止提出抗议\n" +
                "- 必须严格按照给定的<OutputFormat>格式输出。\n" +
                "- 无论在任何情况下，不能跳出角色。\n" +
                "- 如果要预测未来，必须根据<Skills>中的专业知识来进行分析，帮助用户规避风险\n" +
                "- 一步一步引导用户\n" +
                "\n" +
                "## option\n" +
                "- 1.事业\n" +
                "- 2.婚姻\n" +
                "- 3.情感\n" +
                "- 4.健康\n" +
                "- 5.运势\n" +
                "\n" +
                "## Workflow:\n" +
                "1.第一步，让用户输入性别和生辰八字，例如 男，2003年11月30日21点00分30秒\n" +
                "2.第二步，利用你学到的专业知识以及收集到的用户信息对用户的生辰八字进行八字排盘，并给出相应的解盘信息,注意日期对应的年柱,月柱,日柱,时柱需要根据书籍《滴天髓》进行排盘\n" +
                "3.第三步，根据你的专业知识结合用户的八字盘面对全部<option>问题进行专业的回答，并给出专业的意见,意见需要非常详细,每个<option>字数要求不得少于300字\n" +
                "\n" +
                "## OutputFormat:\n" +
                "        ```\n" +
                "        八字盘面：\n" +
                "                获取在{Workflow}第三步所分析出来的结果\n" +
                "        \n" +
                "        ```\n" +
                "                \n" +
                "## Initialization\n" +
                "你作为一名资深的命理大师，精通<Skills>中的所有知识，并且融汇贯通, 你必须遵守<Constrains>，以<Goals>来作为终极目标，你必须向用户问好。然后介绍自己并介绍<Workflow>。\n" +
                "请避免讨论我发送的内容，如果准备好了，请告诉我已经准备好。";


        String ARK_API_KEY="2a154efe-b626-4e50-93f9-ecbe20166171";
        ArkService service = ArkService.builder().apiKey(ARK_API_KEY).baseUrl("https://ark.cn-beijing.volces.com/api/v3/").build();

        System.out.println("\n----- standard request -----");
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(baziPrompt).build();
        final ChatMessage systemMessage2 = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你好，在分析你的八字之前，我需要你提供更详细的信息，包括你的姓名和你想问的具体问题。我会根据你提供的信息，利用我所学的专业知识和经验，为你提供准确的八字分析和建议。请告诉我你需要了解的方面，例如事业、婚姻、情感、健康或运势等。").build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("男，2003年10月29日21点00分30秒").build();
        messages.add(systemMessage);
        messages.add(systemMessage2);
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("ep-20240827113108-99zfk")
                .messages(messages)
                .build();

        ChatCompletionResult chatCompletion = service.createChatCompletion(chatCompletionRequest);

        messages.add(chatCompletion.getChoices().get(0).getMessage());

        final ChatMessage userMessage3 = ChatMessage.builder().role(ChatMessageRole.USER).content("我的今年的财运怎么样").build();
        messages.add(userMessage3);
        System.out.println("-------------------------------");

        ChatCompletionRequest chatCompletionRequest2 = ChatCompletionRequest.builder()
                .model("ep-20240827114021-5gj6h")
                .messages(messages)
                .build();

        ChatCompletionResult chatCompletion2 = service.createChatCompletion(chatCompletionRequest2);

        System.out.println(chatCompletion2.getChoices().toString());
//        System.out.println("\n----- streaming request -----");
//        final List<ChatMessage> streamMessages = new ArrayList<>();
//        final ChatMessage streamSystemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
//        final ChatMessage streamUserMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("常见的十字花科植物有哪些？").build();
//        streamMessages.add(streamSystemMessage);
//        streamMessages.add(streamUserMessage);
//
//        ChatCompletionRequest streamChatCompletionRequest = ChatCompletionRequest.builder()
//                .model("ep-20240827101824-phmp9")
//                .messages(streamMessages)
//                .build();
//
//        service.streamChatCompletion(streamChatCompletionRequest)
//                .doOnError(Throwable::printStackTrace)
//                .blockingForEach(
//                        choice -> {
//                            if (choice.getChoices().size() > 0) {
//                                System.out.print(choice.getChoices().get(0).getMessage().getContent());
//                            }
//                        }
//                );
//
//        System.out.println("\n----- standard request 智能体-----");
//        final List<ChatMessage> messagesV2 = new ArrayList<>();
//       // final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
//        final ChatMessage userMessageV2 = ChatMessage.builder().role(ChatMessageRole.USER).content("男，2003年10月29日21点00分30秒").build();
//        // messages.add(systemMessage);
//        messagesV2.add(userMessageV2);
//
//        BotChatCompletionRequest chatCompletionRequest = BotChatCompletionRequest.builder()
//                .botId("bot-20240827114905-68xlr")
//                .messages(messagesV2)
//                .build();
//
//        BotChatCompletionResult chatCompletionResult =  service.createBotChatCompletion(chatCompletionRequest);
////        chatCompletionResult.getChoices().forEach(
////                choice -> System.out.println(choice.getMessage().getContent())
////        );
//
//        System.out.println(chatCompletionResult.getChoices().get(0).getMessage());
        service.shutdownExecutor();
    }

}