import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Spin, Space, Flex } from 'antd';
import { ReactTyped } from 'react-typed';
import { Typography } from 'antd';
const { Paragraph } = Typography;

const contentStyle = {
  padding: 50,
  background: '#243647',
  borderRadius: 4,
};

const content = <div style={contentStyle} />;

const FinishedPrompt = ({ enhancedPrompt, loading }) => {
  const [lines, setLines] = useState([]);
  const [copyMaterial, setCopyMaterial] = useState('');

   useEffect(() => {
    if (enhancedPrompt !== '') {
      const linesArray = enhancedPrompt.split('<br>');
      setLines(linesArray);
    }
   }, [enhancedPrompt, loading]);
  
   useEffect(() => {
    // Concatenate all lines to form copyMaterial
    setCopyMaterial(lines.join(' '));
  }, [lines]);

  return (
    <div className="grid-container-center-right">
      <div>
        {loading ? (
                <Flex style={{background:'transparent', width:'50%',marginLeft:'25%',marginTop:'20%'}}  gap="small" vertical>
                  <Spin  className='spinner'  tip="Your finished prompt is loading..." size="large">{content}</Spin>
               </Flex>
        ) : enhancedPrompt === '' ? (
          <p style={{ color: '#3b4e6a', textAlign: 'center', fontWeight: 'bold', fontSize: 'larger' }}>
            Your finished prompt will be shown here
          </p>
          ) : (
              
            <>
            {lines.map((line, index) => (
                  <p key={index}>
                    <ReactTyped
                      typeSpeed={2}
                      cursorChar=""
                      strings={[line]}
                      startDelay={index * 10}
                    />
                  </p>
                ))
              }
              <div>
              <Paragraph copyable={{ text: copyMaterial }}>
              </Paragraph>
            </div>
            </>
        )}
      </div>
    </div>
  );
};

FinishedPrompt.propTypes = {
  enhancedPrompt: PropTypes.string.isRequired,
  loading: PropTypes.bool.isRequired
};

export default FinishedPrompt;
