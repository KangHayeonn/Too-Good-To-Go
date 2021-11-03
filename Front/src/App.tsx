import React from "react";
import "./App.css";
import Header from "./components/js/Header";
import Footer from "./components/js/Footer";
import MainBoard from "./components/js/MainBoard";

const App: React.FC = () => {
  return (
    <div className="App">
      <Header />
      <MainBoard />
      <Footer />
      <h1>test</h1>
    </div>
  );
};

export default App;
