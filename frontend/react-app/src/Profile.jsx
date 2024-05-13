import { useEffect, useState } from "react";
import PropTypes from 'prop-types';
import PromptCard from './PromptCard';

const Profil = ({ token }) => {
   const [responses, setResponses] = useState([]); // State to store the fetched responses
   
   useEffect(() => {
      const fetchData = async () => {
         try {
            const response = await fetch('http://localhost:8080/api/myResponses', {
               method: 'GET',
               headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${token}`
               }
            });
            if (!response.ok) {
               throw new Error('Network response was not ok');
            }
            const data = await response.json();
            setResponses(data); // Set the fetched responses in state
         } catch (error) {
            console.error('Error fetching data:', error);
         }
      };

      fetchData();
   }, [token]); // Run the effect whenever the token changes

   return (
      <div style={{width:"100%"}}>
         {responses.map(response => (
            response && (
            <PromptCard 
               key={response.id} // Assuming each response object has a unique ID
               text={response.text}
               length={response.length}
               grade={response.grade}
               promptTokens={response.promptTokens}
               responseTokens={response.responseTokens}
               llm={response.llm}
               userPrompt={response.userPrompt}
               finishedPrompt={response.finishedPrompt}
            />)
         ))}
      </div>
   );
};

Profil.propTypes = {
   token: PropTypes.string.isRequired
};

export default Profil;
