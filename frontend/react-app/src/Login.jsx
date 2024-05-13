import { useState } from 'react';
import PropTypes from 'prop-types';
import { Form, Input, Button, Typography, message } from 'antd';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Link ,useHistory} from 'react-router-dom';

const { Title } = Typography;

const Login = ({ onLogin }) => {
  const [loading, setLoading] = useState(false);
  const [token, setToken] = useState('')
  const [messageApi, contextHolder] = message.useMessage();
  const failLogin = () => {
    messageApi.error('Email or password are incorrect.');
 };
 
 const history = useHistory(); // Get the history object

  const onFinish = (values) => {
    console.log(values)
    setLoading(true);
     const url = "http://localhost:8080/api/authenticate";
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
        failLogin();
        setLoading(false)
        return "X"
    }
    return response.json();
    })
       .then(data => {
         if (data != "X") {
          
           setLoading(false)
           console.log(data.token)
           setToken(data.token)
           console.log(token);
           onLogin(data.token);
           history.push("/"); // Redirect to "/" after successful registration
          }
    })
    .catch(error => {
       console.error("pickica "+error);
      failLogin(); // Call the failRegister function to display an error message
      setLoading(false)
    })

  };

  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

  return (
    <div className="login-container" style={{ textAlign: "center" }}>
      <Title style={{ color: "white"  }} level={1}>Login</Title>
      {contextHolder}
      <Form
        name="basic"
        initialValues={{ remember: true }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed} 
      >
        <Form.Item
        name="email"
        rules={[{ required: true, message: 'Please input your email!' }]}
      >
        <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Email" />
      </Form.Item>
      <Form.Item
        name="password"
        rules={[{ required: true, message: 'Please input your Password!' }]}
      >
        <Input
          prefix={<LockOutlined className="site-form-item-icon" />}
          type="password"
          placeholder="Password"
        />
      </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading}>
            Login
          </Button>
        </Form.Item>

        <Link to="/register" style={{ textDecoration: 'none' }}>
          <span style={{ color: 'white', textDecoration: 'underline', cursor: 'pointer' }}>
            Make an account
          </span>
        </Link> 
      </Form>
    </div>
  );
};

Login.propTypes = {
  onLogin: PropTypes.func.isRequired,
};

export default Login;
