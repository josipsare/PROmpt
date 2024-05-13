import { useState } from 'react';
import PropTypes from 'prop-types';
import { Form, Input, Button, Typography,message } from 'antd';
import { Link, useHistory } from 'react-router-dom'; // Import useHistory hook

const { Title } = Typography;

const Register = ({ onRegister }) => {
   const [loading, setLoading] = useState(false);
   const [token, setToken] = useState('');
   const [messageApi, contextHolder] = message.useMessage();
   const failRegister = () => {
      messageApi.error('Email is already taken. Please try another one.');
      setLoading(false)
   };
   
   const history = useHistory(); // Get the history object


  const onFinish = (values) => {
    console.log(values)
    setLoading(true);
     const url = "http://localhost:8080/api/register";
     fetch(url,{
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(values)
     })
     .then(response => {
        if (!response.ok) {
         console.log("penis")
           failRegister();
           return "X";
      }
      return response.json();
      })
        .then(data => {
           if (data != "X") {
              
              setLoading(false)
              console.log(data.token)
              setToken(data.token)
              console.log(token);
              onRegister(data.token);
              history.push("/"); // Redirect to "/" after successful registration
            }
      })
      .catch(error => {
         console.error("pickica "+error);
         failRegister(); // Call the failRegister function to display an error message
      })

  };

  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

  return (
    <div className="Register-container">
        <Title style={{ color: "white" }} level={2}>Register</Title>
         {contextHolder}   
      <Form
        name="basic"
        initialValues={{ remember: true }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed} 
        >
         <Form.Item
          label={<span style={{ color: 'white' }}>Username</span>}
          name="username"
          rules={[{ required: true, message: 'Please input your email!' }]}
          labelStyle={{ color: 'white' }} // Apply custom style to the label
        >
          <Input />
        </Form.Item>

        <Form.Item
          label={<span style={{ color: 'white' ,marginRight:'28px'}}>Email</span>}
          name="email"
          rules={[{ required: true, message: 'Please input your email!' }]}
          labelStyle={{ color: 'white' }} // Apply custom style to the label
        >
          <Input />
        </Form.Item>

        <Form.Item
          label={<span style={{ color: 'white' ,marginRight:'3px'}}>Password</span>}
          name="password"
          rules={[{ required: true, message: 'Please input your password!' }]}
          labelStyle={{ color: 'white' }} // Apply custom style to the label
        >
          <Input.Password />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading}>
            Register
          </Button>
        </Form.Item>
 
      </Form>
    </div>
  );
};

Register.propTypes = {
  onRegister: PropTypes.func.isRequired,
};

export default Register;
