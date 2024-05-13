import { useState } from 'react';
import { Select, Button } from 'antd';
const { Option } = Select;

function Form() {
  const [typeOfPrompt, setTypeOfPrompt] = useState('');

  const handleTypeOfPromptChange = (value) => {
    setTypeOfPrompt(value);
  };

  return (
    <div>
      <p>What type of prompt is it:</p>
      <Select
        id="typeOfPrompt"
        name="typeOfPrompt"
        value={typeOfPrompt}
        onChange={handleTypeOfPromptChange}
      >
        <Option disabled value="">
          Select a type
        </Option>
      </Select>
      <br /><br />
      <Button onClick={() => handleTypeOfPromptChange('code')}>Code related</Button>
      <Button onClick={() => handleTypeOfPromptChange('general')}>General knowledge</Button>
      <Button onClick={() => handleTypeOfPromptChange('summarize')}>Summarize text</Button>
      <br /><br />
    </div>
  );
}

export default Form;
