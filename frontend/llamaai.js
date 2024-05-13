import LlamaAI from "llamaai";

import {
   saveUserPrompt
} from './saveUserPrompt.js'
import {
   saveResponse
} from "./saveResponse.js";


const apiToken = "LL-InVns38unezRCBkdYmgJiDMz7etXiaKu1sLPn01T9dpdHnqMcGVkwviP80wKE8so";
const llamaAPI = new LlamaAI(apiToken);

var llmID = "2"

export async function LLama(prompt, type) {
   const completion = await llamaAPI
      .run({
         messages: [{
            role: "assistant",
            content: prompt
         }]
      })

   var responseMessage = completion.choices[0].message.content
   console.log(completion.choices[0].message.content)

   try {
      const userPromptIdd = await saveUserPrompt(prompt, type);
      console.log(userPromptIdd); // This will log the userPromptId

      saveResponse(responseMessage, userPromptIdd, llmID)
   } catch (error) {
      console.error('Error:', error); // Handle errors
   }
   return responseMessage

}