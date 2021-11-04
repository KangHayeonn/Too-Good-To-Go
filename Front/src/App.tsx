import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import React from "react";
import "./App.css";
import Header from "./components/organisms/Header/Header";
import MainBoard from "./components";
import Footer from "./components/organisms/Footer/Footer";

const App: React.FC = () => {
	return (
		<div className="App">
			<Router>
				<Header />
				<MainBoard />
				<Footer />
			</Router>
		</div>
	);
};

export default App;
