import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import React from 'react';
import './App.css';
import Header from './components/js/Header';
import MainBoard from './components/js/MainBoard';
import Footer from './components/js/Footer';

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
