import {
   ChatGpt
} from './openai.js'
import {
   LLama
} from './llamaai.js'


export async function putResponses(enhancedPrompt, typeOfPrompt) {

   var responseMessage = await ChatGpt(enhancedPrompt, typeOfPrompt)
   LLama(enhancedPrompt, typeOfPrompt)
   return responseMessage
}