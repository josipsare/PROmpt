import { useState } from 'react';
import { Button, Select, Input, Radio, Flex,Dropdown, Slider, Form , Checkbox} from 'antd';
import PropTypes from 'prop-types';
import { Space, Switch } from 'antd';
const { TextArea } = Input;

const { Option } = Select;

function BetterForm({ onEnhancedPromptChange, onReponseChange, onLoadingChange, token}) {
  const [prompt, setPrompt] = useState('');
  const [typeOfPrompt, setTypeOfPrompt] = useState('');
  const [detail, setDetail] = useState('');
  const [additional, setAdditional] = useState('');
  const [targetLanguage, setTargetLanguage] = useState('');
  const [typeExtras, setTypeExtras] = useState('');
  const [switchValue, setSwitchValue] = useState(true)
  const [responseGenerated, setResponseGenerated] = useState(false)
  const [review, setReview] = useState('')
  const [responseZaReview, setResponseZaReview] = useState('')
  const [temprature, setTemperature] = useState(0);
  const [takeABreath,setTakeABreath] = useState(false)
  const [sliderValue, setSliderValue] = useState(5); // State to store slider value

  const handleTakeABreathChange = (e) => {
    setTakeABreath(e.target.checked);
  };

  const handleTypeOfPromptChange = (value) => {
    setTypeOfPrompt(value);
  };

  const handleTemperatureChange = (value) => {
    setTemperature(value);
  };

  const handleSwitchChange = (checked) => {
    setSwitchValue(checked);
    console.log('Switch value:', checked);
  };

  const handleLanguageSelect = (value) => {
    console.log("u targetLanguageu sam")
    console.log(value)
    setTargetLanguage(value);
    setTypeExtras(prevState => ({
      ...prevState,
      targetLanguage: value
    }));
  };

  const handleReview = (event) => {
    event.preventDefault()
    const reviewData = {
      responseReview: responseZaReview,
      review:sliderValue
    }
    console.log(reviewData);
    
    fetch("http://localhost:8080/api/gradeResponse", {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(reviewData)
    })
      .then(response => response.json())
      .then(data => {
        console.log(data)
      })
      .catch(error => console.error('Error:', error));
  
  }

  const handleAdditionalSelect = (value) => {
    console.log("u additionalu sam")
    console.log(value)
    setAdditional(value);
    setTypeExtras(prevState => ({
      ...prevState,
      additional: value
    }));
  };

  function handleDetailSelect(value) {
    console.log("u detailu sam")
    console.log(value)
    setDetail(value);
    setTypeExtras(prevState => ({
      ...prevState,
      detail: value
    }));
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log("u handle Sumbit")
    console.log(typeExtras)
    onLoadingChange(true)
    setResponseGenerated(false)
  
    const formData = {
      prompt: prompt,
      type: typeOfPrompt,
      temperature: temprature,
      takeABreath:takeABreath,
      typeExtras: JSON.stringify(typeExtras)
    };
    console.log(formData)

    const url = new URL('http://localhost:8080/api/enhanceUserPrompt')
    var model = switchValue ? "LLama-13b":"ChatGpt-3.5-Turbo"
    url.searchParams.append('llm',model)
    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(formData)
    })
      .then(response => response.json())
      .then(data => {
        console.log(data)
        onLoadingChange(false)
        onEnhancedPromptChange(data.finishedPromptText); // Update enhancedPrompt in the parent component
        onReponseChange(data.responseFromLLMText)
        setResponseZaReview(data.responseFromLLMText)
        setResponseGenerated(true)
      })
      .catch(error => console.error('Error:', error));
  };



  return (
    <div className='grid-container'>
      <form onSubmit={handleSubmit}>
        

      <label htmlFor="prompt">Which model of LLM do you want to use:</label><br />
        <Space direction="vertical">
          <Switch onChange={handleSwitchChange} checkedChildren="LLama-13b" unCheckedChildren="ChatGPT-3.5-Turbo" defaultChecked className="customswitch"/>
        </Space>
        <br></br>
        <br></br>

        <label htmlFor="prompt">Prompt:</label><br />
        <TextArea id="prompt" name="prompt" style={{ width:'100%'}} value={prompt} onChange={(e) => setPrompt(e.target.value)} /><br /><br />

        <br />
        <label>Type of prompt</label><br />
        <Flex vertical gap="middle">
          <Radio.Group defaultValue="" buttonStyle="solid">
            <Radio.Button onClick={() => handleTypeOfPromptChange('code')} value="code" style={{ width: 'auto', textAlign: 'center' }}>Code</Radio.Button>
            <Radio.Button onClick={() => handleTypeOfPromptChange('teaching')} value="teaching" style={{ width: 'auto',textAlign:'center' }}>Teaching</Radio.Button>
            <Radio.Button onClick={() => handleTypeOfPromptChange('rephrasing')} value="writing" style={{ width: 'auto',textAlign:'center' }}>Rephrasis</Radio.Button>
            <Radio.Button onClick={() => handleTypeOfPromptChange('writing')} value="writing" style={{ width: 'auto', textAlign: 'center' }}>Writing</Radio.Button>
          </Radio.Group>
        </Flex>
    
        <br />
        <br />
        <label>Level of detail:</label>
        <Flex vertical gap="middle">
        <Radio.Group defaultValue="normal" buttonStyle="solid">
          <Radio.Button onClick={() => handleDetailSelect("no_extra_detail")} value="no_extra_detail">No detail</Radio.Button>
          <Radio.Button onClick={() => handleDetailSelect("normal_detail")} value="normal_detail">Normal detail</Radio.Button>
          <Radio.Button onClick={() => handleDetailSelect("extra_detail")} value="extra_detail">Extra detail</Radio.Button>
        </Radio.Group>
        </Flex>
        <br /><br />  

        <div id="additionalElements" style={{ display: typeOfPrompt === 'code' ? 'block' : 'none'}}>
          <p>What help do you need?</p>
          <Select dropdownStyle={{ backgroundColor: 'lightblue', color: 'black' }} style={{width:'300px'}} id="additionalInputCode" name="additionalInputCode" value={additional} onChange={handleAdditionalSelect}>
            <Option disabled value="">Select a type help needed</Option>
            <Option value="debug">Debug</Option>
            <Option value="translate">Translate into another language</Option>
            <Option value="explain">Explain</Option>
            <Option value="write">Web-Dev</Option>
            <Option value="security">Security</Option>
            <Option value="fullstackdev">Full-Stack Development</Option>
            <Option value="uxui">UX/UI Develompent</Option>
            <Option value="frontenddev">Front-End Develompent</Option>
            <Option value="backenddev">Back-End Develompent</Option>
            <Option value="data_anal">Data Analysis</Option>
          </Select><br /><br />
        </div>

        <div id="additionalElements" style={{ display: typeOfPrompt === 'teaching' ? 'block' : 'none'}}>
          <p>What help do you need?</p>
          <Select  dropdownStyle={{ backgroundColor: 'lightblue', color: 'black' }} style={{width:'300px'}} id="additionalInputCode" name="additionalInputCode" value={additional} onChange={handleAdditionalSelect}>
            <Option disabled value="">Select a type help needed</Option>
            <Option value="philosophy">Philosophy</Option>
            <Option value="history">History</Option>
            <Option value="math">Math</Option>
            <Option value="writing">Writing</Option>
          </Select><br /><br />
        </div>

        <div id="additionalElements" style={{ display: typeOfPrompt === 'rephrasing' ? 'block' : 'none'}}>
          <p>What help do you need?</p>
          <Select  dropdownStyle={{ backgroundColor: 'lightblue', color: 'black' }} style={{width:'300px'}} id="additionalInputCode" name="additionalInputCode" value={additional} onChange={handleAdditionalSelect}>
            <Option disabled value="">Select a type help needed</Option>
            <Option value="exaggerate">Exaggerate</Option>
            <Option value="illuminate">Illuminate</Option>
            <Option value="emphasizegood">Emphasize good</Option>
            <Option value="emphasizebad">Emphasize bad</Option>
            <Option value="formalize">Formalize</Option>
            <Option value="informalize">Informalize</Option>
            <Option value="paraphrase">Paraphrase</Option>

          </Select><br /><br />
        </div>

        <div id="additionalElements" style={{ display: typeOfPrompt === 'writing' ? 'block' : 'none'}}>
          <p>What help do you need?</p>
          <Select  dropdownStyle={{ backgroundColor: 'lightblue', color: 'black' }} style={{width:'300px'}} id="additionalInputCode" name="additionalInputCode" value={additional} onChange={handleAdditionalSelect}>
            <Option disabled value="">Select a type help needed</Option>
            <Option value="storyteller">Storyteller</Option>
            <Option value="poet">Poet</Option>
            <Option value="essaywriter">Essay Writer</Option>
            <Option value="journalist">Journalist</Option>
            <Option value="improve">Improve</Option>
          </Select><br /><br />
        </div>

        <div id="translateExtra" style={{ display: additional === 'translate' ? 'block' : 'none' }}>
          <p>Enter the target language:</p>
          <Select dropdownStyle={{ backgroundColor: 'lightblue', color: 'black' }} id="targetLanguage" name="targetLanguage" value={targetLanguage} onChange={handleLanguageSelect}>
            <Option disabled value="">Select a type</Option>
            <Option value="python">Python</Option>
            <Option value="c">C</Option>
            <Option value="java">Java</Option>
            <Option value="javascript">JavaScript</Option>
          </Select>
          <br /><br />
        </div>

        <div>
        <Checkbox style={{color:"white"}} checked={takeABreath} onChange={handleTakeABreathChange}>
          Take a deep breath
        </Checkbox>
        </div>

        <div>
          <p>Temperature:</p>
        <Slider
            min={0}
            max={1} // 10 corresponds to 1.0 since each step is 0.1
            step={0.1} // Step size is 0.1
            onChange={handleTemperatureChange}
          />
        </div>

        <Button type="primary" htmlType="submit">Submit</Button>
      </form>
      <div style={{ display: responseGenerated ? 'block' : 'none' }}>
      <form onSubmit={handleSubmit}>
        {/* Other form elements */}
        <div style={{ display: responseGenerated ? 'block' : 'none' }}>
          <Slider max={10} defaultValue={5} onChange={setSliderValue} />
          <Button onClick={handleReview}>Review response</Button>
        </div>
      </form>
      </div>
    </div>
  );
}

BetterForm.propTypes = {
  onEnhancedPromptChange: PropTypes.func.isRequired,
  onReponseChange: PropTypes.func.isRequired,
  onLoadingChange: PropTypes.func.isRequired,
  token:PropTypes.string.isRequired
};


export default BetterForm;
