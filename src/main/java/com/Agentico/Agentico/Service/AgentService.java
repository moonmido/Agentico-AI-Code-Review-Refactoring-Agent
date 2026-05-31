package com.Agentico.Agentico.Service;

import com.Agentico.Agentico.Instruction.CodeInstructions;
import com.Agentico.Agentico.Instruction.ReviewInstructions;
import com.Agentico.Agentico.Model.CodeRequest;
import com.Agentico.Agentico.Model.CodeResponse;
import com.Agentico.Agentico.Model.ReviewResponse;
import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;

import java.util.HashMap;
import java.util.Map;

@Agent(description = "Code reviewer and generate new clean code")
public class AgentService {

    @Action(description = "parse user input into code request")
    public CodeRequest toRequest(UserInput userInput) {
        return new CodeRequest(userInput.getContent());
    }

    @Action(description = "review the user code")
    public ReviewResponse review(CodeRequest request, Ai ai) {

        return ai.withLlmByRole("reviewer")
                .withId("code-review")
                .creating(ReviewResponse.class)
                .fromPrompt(
                        ReviewInstructions.REVIEW_DEMANDE.formatted(request.code())
                );
    }

    @Action(description = "generate improved code based on review")
    public CodeResponse generate_code(
            CodeRequest request,
            ReviewResponse reviewResponse,
            Ai ai
    ) {

        return ai.withDefaultLlm()
                .withId("code-generator")
                .creating(CodeResponse.class)
                .fromPrompt(
                        CodeInstructions.CODE_GENERATOR_DEMAND
                                .formatted(request.code(), reviewResponse.review())
                );
    }

    @AchievesGoal(description = "get review and refactored code")
    @Action(description = "combine review and refactored code")
    public Map<String, String> generate(CodeResponse codeResponse) {

        Map<String, String> response = new HashMap<>();
        response.put("code", codeResponse.new_code());
        response.put("review", codeResponse.ai_review());
        return response;
    }
}