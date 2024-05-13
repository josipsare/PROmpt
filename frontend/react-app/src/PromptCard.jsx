import React from 'react';
import PropTypes from 'prop-types';
import { Card } from 'antd';

const { Meta } = Card;

const PromptCard = ({ text,length, grade, llm, userPrompt, finishedPrompt, promptTokens, responseTokens }) => {

   
   
   return (
      <div style={{ width: "80%", margin: "auto", marginBottom: 16 }}>
         <Card >
            <Meta  title={`${userPrompt.prompt}`} />
            <p><b>Finished prompt:</b>{finishedPrompt.text}</p>
            <p><b>Finished prompt length: </b>{finishedPrompt.length}</p>
            <p><b>Finished prompt tokens: </b>{promptTokens}</p>
            <p><b>Response from LLM: </b>{text}</p>
            <p><b>Response length: </b>{length}</p>
            <p><b>Response tokens: </b>{responseTokens}</p>
            <p><b>Grade: </b>{grade}</p>
            <p><b>Date:  </b> {userPrompt.date}</p>
         </Card>
      </div>
   );
};

PromptCard.propTypes = {
  text: PropTypes.string.isRequired,
   grade: PropTypes.number.isRequired,
   length: PropTypes.number.isRequired,
   promptTokens: PropTypes.number.isRequired,
   responseTokens:PropTypes.number.isRequired,
  llm: PropTypes.shape({
    name: PropTypes.string.isRequired,
    id: PropTypes.number.isRequired,
  }).isRequired,
  userPrompt: PropTypes.shape({
    prompt: PropTypes.string.isRequired,
    date: PropTypes.string.isRequired,
    // Additional user prompt prop types can be defined here
  }).isRequired,
  finishedPrompt: PropTypes.shape({
    length: PropTypes.number.isRequired,
    text: PropTypes.string.isRequired,
    // Additional finished prompt prop types can be defined here
  }).isRequired,
};

export default PromptCard;
