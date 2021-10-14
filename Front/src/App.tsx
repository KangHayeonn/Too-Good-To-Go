import React from 'react';
import './App.css';
import Header from './components/js/Header';
import Footer from './components/js/Footer';
import MainBoard from './components/js/MainBoard';

function App() {
  return (
    <div className="App">
      <Header />
      <h2>Front-End 초기 세팅 완료</h2>
      <MainBoard />
      <Footer />
    </div>
  );
}

export default App;
