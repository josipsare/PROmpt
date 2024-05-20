import { useState } from 'react';
import './Nav.css'
import FinishedPrompt from './FinishedPrompt';
import MyResponse from './MyResponse';
import BetterForm from './BetterForm';
import Logo from './Logo'; // Import the Logo component
import {
  DesktopOutlined,
  FileOutlined,
  TeamOutlined,
  UserOutlined,
} from '@ant-design/icons';
import { Layout, Menu, theme } from 'antd';
import { BrowserRouter as Router, Route, Switch, Link } from 'react-router-dom'; // Import Link from react-router-dom
import LLM from './LLM';
import Login from './Login';
import Register from './Register';
import Profil from './Profile';

const { Content, Footer, Sider } = Layout;

function getItem(label, key, icon, path, children) {
  return {
    key,
    icon,
    path, // Add path property to the item
    children,
    label,
  };
}

const items = [
  getItem('Profile', '1', <UserOutlined />, '/profile'), // Add path for LLM
];

const App = () => {
   const [enhancedPrompt, setEnhancedPrompt] = useState('');
   const [response, setResponse] = useState('')
   const [loading, setIsLoading] = useState(false)
   const [token, setToken] = useState('');
   const [authenticated, setAuthenticated] = useState(false); // State to track authentication status

   // Function to handle login
   const handleLogin = (value) => {
      setToken(value);
      console.log(value);
      setAuthenticated(true);
   };

   const handleRegister = (value) => {
      setToken(value);
      console.log(value);
      setAuthenticated(true);
   };

   const handleLoadingChange = (value) => {
      setIsLoading(value)
    }
  
   
  const handleEnhancedPromptChange = (value) => {
    setEnhancedPrompt(value);
   };
   const handleResponseChange = (value) => {
      setResponse(value)
   };
  const [collapsed, setCollapsed] = useState(true);

  return (
     <Router>
        {!authenticated ? (
         <Layout style={{ minHeight: '100vh', display: 'flex', justifyContent: 'center', alignItems: 'center', background: '#001529db' }}>
         <Switch>
            <Route path="/register">
               <div style={{ width: '50%' }}>
                  <Register onRegister={handleRegister} />
               </div>
            </Route>
            <Route path="/">
               <div style={{ width: '50%' }}> {/* Adjust the width as needed */}
               <Login onLogin={handleLogin} />
               </div>
            </Route> 
         </Switch>
         </Layout>
           ): (    
      <Layout style={{ minHeight: '100vh', width: '100%', background: '#001529db' }}>
        <Sider collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
         <Link to="/">
         <Logo />
         </Link>                  
            <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
              {items.map(item => (
                <Menu.Item key={item.key} icon={item.icon}>
                  <Link to={item.path}>{item.label}</Link> {/* Use Link component for navigation */}
                </Menu.Item>
              ))}
            </Menu>
        </Sider>
            <Layout style={{ minWidth: '0' , background:'transparent'}}>
            <Content style={{ margin: '24px 16px 0', overflow: 'initial', background:"transparent"}}>
               <div
                  className="site-layout-background"
                  style={{ padding: 24, minHeight: 360, background: 'transparent', display: "grid", gridTemplateColumns: "1fr 1fr 1fr" }}
                        >
                  <Switch>
                     <Route path="/profile">
                        <Profil token={token} />    
                     </Route> 
                     <Route path="/llm">
                        <LLM/>
                     </Route>            
                     <Route path="/">          
                        <BetterForm style={{border:"none"}} onEnhancedPromptChange={handleEnhancedPromptChange} onReponseChange={handleResponseChange} onLoadingChange={handleLoadingChange} token={token} />
                        <FinishedPrompt enhancedPrompt={enhancedPrompt} loading={loading} />
                        <MyResponse response={response} loading={loading}/>
                     </Route>
                  </Switch>
               </div>
            </Content>
            <Footer style={{ textAlign: 'center', background:'#001529d4', color:"white" }}>PROmpt ©{new Date().getFullYear()} Created by Josip Šare</Footer>
            </Layout>
      </Layout>
           )}
   </Router>
  );
};

export default App;
