import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Spin, Flex } from 'antd';
import { ReactTyped } from 'react-typed';
import { Typography } from 'antd';
const { Paragraph } = Typography;

const contentStyle = {
  padding: 50,
  background: '#243647',
  borderRadius: 4,
};

const content = <div style={contentStyle} />;

const MyResponse = ({ response, loading }) => {
  const [lines, setLines] = useState([]);
  const [copyMaterial, setCopyMaterial] = useState('');

  useEffect(() => {
    if (response !== '') {
      const linesArray = response.split('<br>').map(line => line.replace(/</g, '&lt;').replace(/>/g, '&gt;'));
      setLines(linesArray);
    }
  }, [response, loading]);

  const escapeHTML = (html) => {
    return html.replace(/</g, '&lt;').replace(/>/g, '&gt;');
  };

  useEffect(() => {
    // Concatenate all lines to form copyMaterial
    setCopyMaterial(lines.join(' '));
  }, [lines]);

  return (
    <div className='grid-container-center-right'>
      <div>
        {loading ? (
          <Flex style={{background:'transparent', width:'50%',marginLeft:'25%',marginTop:'20%'}} gap='small' vertical>
            <Spin className='spinner' tip='Response from LLM is loading...' size="large">{content}</Spin>
          </Flex>
        ) : response === '' ? (
          <p style={{ color: '#3b4e6a', textAlign: 'center', fontWeight: 'bold', fontSize: 'larger' }}>
            Your finished prompt will be shown here
          </p>
        ) : (
          <>
          {lines.map((line, index) => (
                <p key={index} style={{ maxWidth: '100%', overflowX: 'hidden' }}>
                <ReactTyped
                  typeSpeed={3}
                  cursorChar=''
                  strings={[escapeHTML(line)]}
                  startDelay={index * 10}
                />
              </p>
            ))}
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

MyResponse.propTypes = {
  response: PropTypes.string.isRequired,
  loading: PropTypes.bool.isRequired
};

export default MyResponse;
