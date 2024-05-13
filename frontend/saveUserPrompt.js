 export async function saveUserPrompt(prompt, type) {
    try {
       const response = await fetch("http://localhost:8080/api/userPrompt", {
          method: 'POST',
          headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
          },
          body: JSON.stringify({
             prompt: prompt,
             type: type
          })
       });

       if (!response.ok) {
          throw new Error('Failed to save user prompt');
       }

       const json = await response.json();
       console.log(json);
       return json.id;
    } catch (error) {
       console.error('Error:', error);
       throw error;
    }
 }