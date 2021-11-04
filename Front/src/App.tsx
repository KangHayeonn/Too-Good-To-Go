import React from "react";
import "./App.css";
import Header from "./components/organisms/Header/Header";
import Footer from "./components/organisms/Footer/Footer";
import Router from "./Router";

const App: React.FC = () => {
	return (
		<div className="App">
			<Header />
			<Router />
			<Footer />
		</div>
	);
};

export default App;
