import OpenAI from "openai";
import {
   saveUserPrompt
} from './saveUserPrompt.js'
import {
   saveResponse
} from "./saveResponse.js";

const openai = new OpenAI({
   apiKey: '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'
});

var llmID = "1"

export async function ChatGpt(prompt, type) {
   const completion = await openai.chat.completions.create({
      messages: [{
         role: "assistant",
         content: prompt
      }],
      model: "gpt-3.5-turbo",
   });

   var responseMessage = completion.choices[0].message.content
   console.log(responseMessage);
   try {
      const userPromptIdd = await saveUserPrompt(prompt, type);
      console.log(userPromptIdd); // This will log the userPromptId

      saveResponse(responseMessage, userPromptIdd, llmID)
   } catch (error) {
      console.error('Error:', error); // Handle errors
   }
   return responseMessage
   // Chain the second fetch call inside the then block of the first await function

}