import LlamaAI from "llamaai";

async function mainChat() {
   /** 
    * 
   const stream = await openai.chat.completions.create({
      model: "gpt-4",
      messages: [{
         role: "user",
         content: "how are you!"
      }],
      stream: true,
   });
   for await (const chunk of stream) {
      print(chunk.choices[0]);
   }
   */
   await fetch("https://api.openai.com/v1/chat/completions", {
         method: "POST",
         body: JSON.stringify({
            "model": "gpt-3.5-turbo",
            "messages": [{
               "role": "user",
               "content": "how are you!"
            }],
            "temperature": 0.7
         }),
         headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer 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"
         }
      }).then((Response) => Response.json())
      .then((json) => {
         // Extract the 'content' field from each message and log it
         const contents = json.choices.map(choice => choice.message.content);
         console.log(contents);
      })
}



const apiToken = "LL-InVns38unezRCBkdYmgJiDMz7etXiaKu1sLPn01T9dpdHnqMcGVkwviP80wKE8so";
const llamaAPI = new LlamaAI(apiToken);

const apiRequestJson = {
   messages: [{
      role: "user",
      content: "What is the weather like in Boston?"
   }],
   functions: [{
      name: "get_current_weather",
      description: "Get the current weather in a given location",
      parameters: {
         type: "object",
         properties: {
            location: {
               type: "string",
               description: "The city and state, e.g. San Francisco, CA",
            },
            days: {
               type: "number",
               description: "for how many days ahead you wants the forecast",
            },
            unit: {
               type: "string",
               enum: ["celsius", "fahrenheit"]
            },
         },
      },
      required: ["location", "days"],
   }, ],
   stream: false,
   function_call: "get_current_weather",
};

llamaAPI
   .run(apiRequestJson)
   .then((response) => {
      console.log(response.json)
   })
   .catch((error) => {
      // Handle errors
   });


mainChat();