export async function saveResponse(responseMessage, userPromptIdd, llmID) {
   const response = await fetch("http://localhost:8080/api/response", {
      method: 'POST',
      headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
      },
      body: JSON.stringify({
         text: responseMessage,
         llmID: llmID,
         grade: "0",
         userPromptID: parseInt(userPromptIdd)
      })
   })

   if (!response.ok) {
      throw new Error('Failed to save response');
   }
}