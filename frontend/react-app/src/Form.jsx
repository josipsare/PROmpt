import { useState } from 'react';

function Form() {
  const [prompt, setPrompt] = useState('');
  const [typeOfPrompt, setTypeOfPrompt] = useState('');
  const [detail, setDetail] = useState('');
  const [additional, setAdditional] = useState('');
  const [targetLanguage, setTargetLanguage] = useState('');
  const [enhancedPrompt, setEnhancedPrompt] = useState('');
   const [lines, setLines]=useState('');

  const handleSubmit = (event) => {
   event.preventDefault();

   const formData = {
      prompt: prompt,
      type: typeOfPrompt,
      typeExtras: JSON.stringify({ detail: detail, additional: additional })
   };

   fetch('http://localhost:8080/api/enhanceUserPrompt', {
      method: 'POST',
      headers: {
         'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
   })
   .then(response => response.json())
   .then(data => {
      setEnhancedPrompt(data.text);
      setLines(data.text.split('<br>'))
   })
   .catch(error => console.error('Error:', error));
   };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <label htmlFor="prompt">Prompt:</label><br />
        <textarea id="prompt" name="prompt" value={prompt} onChange={(e) => setPrompt(e.target.value)} /><br /><br />

        <p>What type of prompt is it:</p>
        <select id="typeOfPrompt" name="typeOfPrompt" value={typeOfPrompt} onChange={(e) => setTypeOfPrompt(e.target.value)}>
          <option disabled selected value="">Select a type</option>
          <option value="code">Code related</option>
          <option value="general">General knowledge</option>
          <option value="summarize">Summarize text</option>
        </select><br /><br />

        <p>Level of detail:</p>
        <select id="detail" name="detail" value={detail} onChange={(e) => setDetail(e.target.value)}>
          <option disabled selected value="">Select a level of detail</option>
          <option value="no_extra_detail">No extra detail</option>
          <option value="normal_detail">Normal detail</option>
          <option value="extra_detail">Extra amount of detail</option>
        </select><br /><br />

        <div id="additionalElements" style={{ display: typeOfPrompt === 'code' ? 'block' : 'none' }}>
          <p>What help do you need?</p>
          <select id="additionalInputCode" name="additionalInputCode" value={additional} onChange={(e) => setAdditional(e.target.value)}>
            <option disabled selected value="">Select a type</option>
            <option value="debug">Debug</option>
            <option value="translate">Translate into another language</option>
            <option value="explain">Explain</option>
            <option value="write">Write</option>
            <option value="security">Security</option>
            <option value="fullstackdev">Full-Stack Development</option>
            <option value="uxui">UX/UI Develompent</option>
            <option value="frontenddev">Front-End Develompent</option>
          </select><br /><br />
        </div>

        <div id="translateExtra" style={{ display: additional === 'translate' ? 'block' : 'none' }}>
          <p>Enter the target language:</p>
          <select id="targetLanguage" name="targetLanguage" value={targetLanguage} onChange={(e) => setTargetLanguage(e.target.value)}>
            <option disabled selected value="">Select a type</option>
            <option value="python">Python</option>
            <option value="c">C</option>
            <option value="java">Java</option>
            <option value="javascript">JavaScript</option>
          </select>
          <br /><br />
        </div>

        <button type="submit">Submit</button>
      </form>
      <div id="output" style={{ whiteSpace: 'pre-line' }}>
      {enhancedPrompt && (
         <>
            <p><strong>Finished prompt:</strong></p>
            <div>
               {lines.map((line, index) => (
               <p key={index}>{line}</p>
               ))}
            </div>
         </>
         )}
      </div>
    </div>
  );
}

export default Form;
